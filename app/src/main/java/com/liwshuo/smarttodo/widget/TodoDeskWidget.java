package com.liwshuo.smarttodo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.activity.TodoAddActivity;
import com.liwshuo.smarttodo.activity.TodoDetailActivity;
import com.liwshuo.smarttodo.data.DBManager;
import com.liwshuo.smarttodo.service.InitIntentService;
import com.liwshuo.smarttodo.utils.LogUtil;
import com.liwshuo.smarttodo.utils.TimeUtils;

/**
 * Implementation of App Widget functionality.
 */
public class TodoDeskWidget extends AppWidgetProvider {
    private final String TAG = TodoDeskWidget.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            RemoteViews remoteViews = updateAppWidgetListView(context, appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i],remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[i], R.id.todoListWidget);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
    }

    private RemoteViews updateAppWidgetListView(Context context, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.todo_desk_widget);
       Intent intent = new Intent(context, TodoWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteViews.setRemoteAdapter(R.id.todoListWidget, intent); //设置remoteview的适配器
        Intent intent1 = new Intent();
 //       intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent1.setClass(context, TodoDeskWidget.class);
        //  remoteViews.setOnClickFillInIntent(R.id.todoWidgetType, intent);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.todoListWidget, pendingIntent); //配合进行控件的点击操作，imageview
        remoteViews.setTextViewText(R.id.currentDateWidgetView, new TimeUtils().getCurrentDate());
        Intent intent2 = new Intent();
        intent2.setClass(context, TodoAddActivity.class);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.addWidgetView, pendingIntent1);
      //  Intent intent2 = new Intent();
     //   intent2.setClass(context, TodoDetailActivity.class);
    //    remoteViews.setOnClickFillInIntent(R.id.todoListWidget, intent2);
//        remoteViews.setRemoteAdapter(R.id.todoListWidget, intent);

        return remoteViews;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.d(TAG, "Receive");
        int id = intent.getIntExtra("id", -1);

        if (id != -1) {
            if (DBManager.getInstance().getTodoType(id) == 0) {
                DBManager.getInstance().setTodoUndone(id);
            } else {
                DBManager.getInstance().setTodoDone(id);
            }
        }
        super.onReceive(context, intent);
    }
}


