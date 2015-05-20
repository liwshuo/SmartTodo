package com.liwshuo.smarttodo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.activity.TodoDetailActivity;
import com.liwshuo.smarttodo.data.DBManager;
import com.liwshuo.smarttodo.utils.AppConfig;
import com.liwshuo.smarttodo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwshuo on 2015/5/10.
 */
public class TodoListFactory implements RemoteViewsService.RemoteViewsFactory {

    private final String TAG = TodoListFactory.class.getSimpleName();
    private Cursor todoCursor = null;
    private Context context;
    private int appWidgetId;
    public TodoListFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        LogUtil.d(TAG, "appwidgetId = " + appWidgetId);
    }
    @Override
    public void onCreate() {
        LogUtil.d(TAG, "oncreate");
        todoCursor = DBManager.getInstance().getTodayTodoContainsUndone();
    }

    @Override
    public void onDataSetChanged() {
        LogUtil.d(TAG, "data changed");
        todoCursor = DBManager.getInstance().getTodayTodoContainsUndone();
    }

    @Override
    public void onDestroy() {
        todoCursor = null;
    }

    @Override
    public int getCount() {
        return todoCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        todoCursor.moveToPosition(position);
        LogUtil.d(TAG, position+"");
        remoteViews.setTextViewText(R.id.todoWidgetItem, todoCursor.getString(todoCursor.getColumnIndex(AppConfig.TODO_TITLE_COLUMN)));
        if (todoCursor.getInt(todoCursor.getColumnIndex(AppConfig.TODO_TYPE_COLUMN)) == AppConfig.TODO_DONE_TYPE) {
            remoteViews.setTextColor(R.id.todoWidgetItem, Color.GRAY);
            remoteViews.setInt(R.id.todoWidgetItem, "setPaintFlags", Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        //    remoteViews.
            remoteViews.setImageViewResource(R.id.todoWidgetType, R.drawable.done);
        } else {
            remoteViews.setTextColor(R.id.todoWidgetItem,Color.BLACK);
            remoteViews.setInt(R.id.todoWidgetItem, "setPaintFlags", Paint.ANTI_ALIAS_FLAG);
            remoteViews.setImageViewResource(R.id.todoWidgetType, R.drawable.undone);
        }
        Intent intent = new Intent();
        Bundle extras = new Bundle();
        extras.putInt("id", todoCursor.getInt(todoCursor.getColumnIndex(AppConfig.TODO_ID_COLUMN)));
        extras.putIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});
        intent.putExtras(extras);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
   //     intent.setClass(context, TodoDeskWidget.class);
        remoteViews.setOnClickFillInIntent(R.id.todoWidgetType, intent);
    //    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickFillInIntent(R.id.todoWidgetItem, intent);
     //   remoteViews.setOnClickPendingIntent(R.id.todoWidgetType,pendingIntent);
    //    remoteViews.setOnClickPendingIntent(R.id.todoWidgetItem, pendingIntent  );
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
