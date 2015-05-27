package com.liwshuo.smarttodo.receiver;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.activity.TodoListActivity;
import com.liwshuo.smarttodo.data.DBManager;
import com.liwshuo.smarttodo.data.TodoMsg;
import com.liwshuo.smarttodo.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 拦截接收短信的广播，过滤要自动创建Todo的短信
 */
public class SmsReceiver extends BroadcastReceiver {
    public SmsReceiver() {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Map<String, String> expressMap = new HashMap<>();
        expressMap.put("圆通", "圆通快递");
        expressMap.put("韵达", "韵达快递");
        expressMap.put("申通", "申通快递");
        expressMap.put("中通", "中通快递");
        expressMap.put("顺丰", "顺丰快递");
        expressMap.put("天天", "天天快递");
        expressMap.put("全峰", "全峰快递");
        expressMap.put("邮政", "邮政快递");
        expressMap.put("EMS", "EMS");
        expressMap.put("宅急送", "宅急送");
        expressMap.put("如风达", "如风达");
        expressMap.put("优速", "优速快递");

        Log.d("com.bupt.shuo.smsfilter", "haha");
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] smsMessages = new SmsMessage[pdus.length];
        for (int i = 0; i < smsMessages.length; i++) {
            smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        }
        String address = smsMessages[0].getOriginatingAddress();
        String fullMessage = "";
        for (SmsMessage smsMessage : smsMessages) {
            fullMessage = fullMessage + smsMessage.getMessageBody();
        }
        Log.d("com.bupt.shuo.smsfilter", fullMessage);
        if (fullMessage.contains("快递") || fullMessage.contains("kuaidi")) {
            Log.d("com.bupt.shuo.smsfilter", "kuaidi");
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setSmallIcon(R.drawable.notify);
            mBuilder.setTicker("有快递来咯");
            mBuilder.setContentTitle("快递");
            mBuilder.setContentText("有新快递，快去领取吧");
            TodoMsg todoMsg = new TodoMsg();
            String title = "快递";
            for (String key : expressMap.keySet()) {
                if (fullMessage.contains(key)) {
                    title = expressMap.get(key);
                    break;
                }
            }
            todoMsg.setTodoTitle(title);
            todoMsg.setTodoNote(fullMessage);
            todoMsg.setTodoCreateDate(new TimeUtils().getCurrentDate());
            todoMsg.setTodoDate(new TimeUtils().getCurrentDate());
            todoMsg.setTodoTime("18:00");
            todoMsg.setTodoType(1);
            DBManager.getInstance().addTodo(todoMsg);

            Intent resultIntent = new Intent(context, TodoListActivity.class);
            //        resultIntent.putExtra("sms", fullMessage);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addParentStack(TodoListActivity.class);
            taskStackBuilder.addNextIntent(resultIntent);
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            notificationManager.notify(1, mBuilder.build());
            abortBroadcast();
        }
    }
}
