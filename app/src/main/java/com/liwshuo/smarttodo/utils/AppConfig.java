package com.liwshuo.smarttodo.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.liwshuo.smarttodo.widget.TodoDeskWidget;

/**
 * 提供了一些常量
 * Created by shuo on 2015/4/16.
 */
public class AppConfig {
    private static final String TAG = AppConfig.class.getSimpleName();
    public static final String TABLE_NAME = "todo_list";
    public static final String DB_NAME = "todo.db";
    public static final int DB_VERSION = 2;
    public static final String TODO_ID_COLUMN = "_id";
    public static final String TODO_TITLE_COLUMN = "todo_title";
    public static final String TODO_NOTE_COLUMN = "todo_note";
    public static final String TODO_DATE_COLUMN = "todo_date";
    public static final String TODO_TIME_COLUMN = "todo_time";
    public static final String TODO_REPEAT_WEEK_COLUMN = "todo_repeat_week";
    public static final String TODO_REPEAT_MONTH_COLUMN = "todo_repeat_month";
    public static final String TODO_COLOR_COLUMN = "todo_color";
    public static final String TODO_TYPE_COLUMN = "todo_type";
    public static final String TODO_CREATE_TIME_COLUMN = "todo_create_time";
    public static final String TODO_UPDATE_TIME_COLUMN = "todo_update_time";
    public static final String TODO_TAG_COLUMN = "todo_tag";
    public static final String TODO_CREATE_DATE_COLUMN = "todo_create_date";
    public static final String TODO_UPDATE_DATE_COLUMN = "todo_update_date";
    public static final int TODO_LATER_TYPE = 2;
    public static final int TODO_TODAY_TYPE = 1;
    public static final int TODO_DONE_TYPE = 0;

    public static void updateWidget(Context context) {
        LogUtil.d(TAG, context.getPackageName());
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context.getPackageName(), TodoDeskWidget.class.getName()));
        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        context.sendBroadcast(intent);
    }
}
