package com.liwshuo.smarttodo.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.data.DBManager;
import com.liwshuo.smarttodo.utils.AppConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwshuo on 2015/5/10.
 */
public class TodoListFactory implements RemoteViewsService.RemoteViewsFactory {

    private Cursor todoCursor = null;
    private Context context;
    private int appWidgetId;
    public TodoListFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    @Override
    public void onCreate() {
        todoCursor = DBManager.getInstance().getTodayTodo();
    }

    @Override
    public void onDataSetChanged() {

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
        remoteViews.setTextViewText(R.id.todoWidgetItem, todoCursor.getString(todoCursor.getColumnIndex(AppConfig.TODO_TITLE_COLUMN)));

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
