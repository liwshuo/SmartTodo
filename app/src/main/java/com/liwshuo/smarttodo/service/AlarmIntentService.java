package com.liwshuo.smarttodo.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;


import com.liwshuo.smarttodo.data.TodoMsg;
import com.liwshuo.smarttodo.receiver.AlarmReceiver;
import com.liwshuo.smarttodo.utils.LogUtil;

import java.util.Calendar;

/**
 * 在该IntentService中创建提醒
 */
public class AlarmIntentService extends IntentService {

    private static final String TAG = AlarmIntentService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param
     */
    public AlarmIntentService() {  //intentservice的构造函数必须传入name，用来命名进程的名称，用来调试用的。这里我们使用自己的构造函数来调用父类的构造函数，name使用类名
        super(TAG);
    }


    public static void actionStart(Context context, TodoMsg todoMsg) {
        Intent intent = new Intent(context, AlarmIntentService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("todoMsg", todoMsg);
        intent.putExtras(bundle);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.d(TAG,"onHandleIntent");
        Bundle bundle = intent.getExtras();
        TodoMsg todoMsg = bundle.getParcelable("todoMsg");
        String notifyDate = todoMsg.getTodoDate();
        String notifyTime = todoMsg.getTodoTime();
        String notifyRepeat = null;
        String title = todoMsg.getTodoTitle();
        int requestCode = todoMsg.getTodoRequestCode();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerTime = getTimeMillis(notifyDate, notifyTime);
        if(triggerTime > System.currentTimeMillis()){
            Intent broadcastIntent = new Intent(AlarmIntentService.this, AlarmReceiver.class);
            broadcastIntent.putExtra("message", title);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            LogUtil.d(TAG, ""+triggerTime);
            LogUtil.d(TAG, ""+System.currentTimeMillis());
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }

    private long getTimeMillis(String date, String time) {
        String[] dateArray = date.split("-");
        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1])-1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }



    private long getRepeatMillis(String date, String time, String repeat) {
        return 0;
    }
}
