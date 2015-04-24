package com.liwshuo.smarttodo.control;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.liwshuo.smarttodo.data.TodoMsg;
import com.liwshuo.smarttodo.utils.AppConfig;
import com.liwshuo.smarttodo.utils.LogUtil;

/**
 * Created by liwshuo on 2015/4/21.
 */
public abstract class BaseAdapter extends CursorAdapter {

    public BaseAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public int getTodoMsgId(int i) {
        int _id = -1;
        Cursor cursor = getCursor();
        if (cursor.moveToPosition(i)) {
            _id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        }
        return _id;
    }

    public TodoMsg getTodoMsg(int i) {
        TodoMsg todoMsg = new TodoMsg();
        Cursor cursor = getCursor();
        if (cursor.moveToPosition(i)) {
            todoMsg.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(AppConfig.TODO_ID_COLUMN)));
            todoMsg.setTodoTitle(cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_TITLE_COLUMN)));
            LogUtil.d("adapter", "aa" + cursor.getString(cursor.getColumnIndexOrThrow(AppConfig.TODO_TITLE_COLUMN)));
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
        }
        return todoMsg;
    }
}
