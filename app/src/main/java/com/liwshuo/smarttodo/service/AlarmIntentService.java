package com.liwshuo.smarttodo.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;


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
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AlarmIntentService() {  //intentservice的构造函数必须传入name，用来命名进程的名称，用来调试用的。这里我们使用自己的构造函数来调用父类的构造函数，name使用类名
        super(TAG);
    }


    public static void actionStart(Context context, String notifyDate, String notifyTime, String notifyRepeat, String message) {
        Intent intent = new Intent(context, AlarmIntentService.class);
        Bundle bundle = new Bundle();
        bundle.putString("notifyDate", notifyDate);
        bundle.putString("notifyTime", notifyTime);
        bundle.putString("notifyRepeat", notifyRepeat);
        bundle.putString("message", message);
        intent.putExtras(bundle);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.d(TAG,"onHandleIntent");
        Bundle bundle = intent.getExtras();
        String notifyDate = bundle.getString("notifyDate");
        String notifyTime = bundle.getString("notifyTime");
        String notifyRepeat = bundle.getString("notifyRepeat");
        String message = bundle.getString("message");
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerTime = getTimeMillis(notifyDate, notifyTime);
        if(triggerTime > System.currentTimeMillis()){
            int requestCode = getRequestCode();
            Intent broadcastIntent = new Intent(AlarmIntentService.this, AlarmReceiver.class);
            broadcastIntent.putExtra("message", message);
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

    private int getRequestCode() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int code = year * 100000 + month * 10000 + day * 1000 + hour * 100 + minute * 10 + second;
        return code;
    }

    private long getRepeatMillis(String date, String time, String repeat) {
        return 0;
    }
}
