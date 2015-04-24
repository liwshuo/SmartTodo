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


public class SmsReceiver extends BroadcastReceiver {
    public SmsReceiver() {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
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
            mBuilder.setTicker("sms is coming");
            mBuilder.setContentTitle("create new todo");
            mBuilder.setContentText("快递");
            TodoMsg todoMsg = new TodoMsg();
            todoMsg.setTodoTitle("快递");
            todoMsg.setTodoNote(fullMessage);
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