package com.liwshuo.smarttodo.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;


import com.liwshuo.smarttodo.data.DBManager;
import com.liwshuo.smarttodo.data.TodoMsg;
import com.liwshuo.smarttodo.service.AlarmIntentService;
import com.liwshuo.smarttodo.utils.AppConfig;
import com.liwshuo.smarttodo.utils.LogUtil;

import java.util.Calendar;

public class AutoStartReceiver extends BroadcastReceiver {
    public AutoStartReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Toast.makeText(context, "auto start", Toast.LENGTH_LONG).show();
            DBManager dbManager = new DBManager();
            Cursor cursor = dbManager.getUndoneTodo();
            while (cursor.moveToNext()) {
                AlarmIntentService.actionStart(context, getTodoMsg(cursor));
            }
        }
    }

    public TodoMsg getTodoMsg(Cursor cursor) {
        TodoMsg todoMsg = new TodoMsg();
        todoMsg.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(AppConfig.TODO_ID_COLUMN)));
        todoMsg.setTodoTitle(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_TITLE_COLUMN)));
        todoMsg.setTodoNote(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_NOTE_COLUMN)));
        todoMsg.setTodoType(cursor.getInt(cursor.getColumnIndexOrThrow(AppConfig.TODO_TYPE_COLUMN)));
        todoMsg.setTodoColor(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_COLOR_COLUMN)));
        todoMsg.setTodoCreateTime(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_CREATE_TIME_COLUMN)));
        todoMsg.setTodoDate(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_DATE_COLUMN)));
        todoMsg.setTodoRepeatMonth(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_REPEAT_MONTH_COLUMN)));
        todoMsg.setTodoRepeatWeek(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_REPEAT_WEEK_COLUMN)));
        todoMsg.setTodoUpdateTime(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_UPDATE_TIME_COLUMN)));
        todoMsg.setTodoTime(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_TIME_COLUMN)));
        todoMsg.setTodoTag(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_TAG_COLUMN)));
        todoMsg.setTodoCreateDate(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_CREATE_DATE_COLUMN)));
        todoMsg.setTodoUpdateDate(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_UPDATE_DATE_COLUMN)));
        return todoMsg;
    }
}
