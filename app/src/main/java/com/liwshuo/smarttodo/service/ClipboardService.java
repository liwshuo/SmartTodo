package com.liwshuo.smarttodo.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.activity.TodoListActivity;
import com.liwshuo.smarttodo.data.DBManager;
import com.liwshuo.smarttodo.data.TodoMsg;
import com.liwshuo.smarttodo.utils.TimeUtils;


/**
 * Created by shuo on 2015/4/16.
 */
public class ClipboardService extends Service {
    ClipboardManager clipboardManager;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ClipboardService.class);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        clipboardManager  = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardListener());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class ClipboardListener implements ClipboardManager.OnPrimaryClipChangedListener {

        @Override
        public void onPrimaryClipChanged() {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
            mBuilder.setSmallIcon(R.drawable.baymaxnotify);
            mBuilder.setTicker("有新Todo");
            mBuilder.setContentTitle("Todo");
            mBuilder.setContentText(clipboardManager.getPrimaryClip().getItemAt(0).getText());
            Intent resultIntent = new Intent(getApplicationContext(), TodoListActivity.class);
            resultIntent.putExtra("sms", clipboardManager.getPrimaryClip().getItemAt(0).getText());
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
            taskStackBuilder.addParentStack(TodoListActivity.class);
            taskStackBuilder.addNextIntent(resultIntent);
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            notificationManager.notify(2,mBuilder.build());
            TodoMsg todoMsg = new TodoMsg();
            todoMsg.setTodoTitle("剪贴板");
            todoMsg.setTodoNote(clipboardManager.getPrimaryClip().getItemAt(0).getText().toString());
            todoMsg.setTodoType(1);
            todoMsg.setTodoDate(new TimeUtils().getCurrentDate());
            todoMsg.setTodoTime(new TimeUtils().getCurrentTimeWithoutSecond());
            DBManager.getInstance().addTodo(todoMsg);
        }
    }
}
