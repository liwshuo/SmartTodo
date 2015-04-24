package com.liwshuo.smarttodo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liwshuo.smarttodo.utils.AppConfig;


/**
 * Created by shuo on 2015/4/16.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = MySQLiteOpenHelper.class.getSimpleName();
    private final String CREATE_TODO_LIST = "create table " + AppConfig.TABLE_NAME +
            " (_id integer primary key autoincrement, " +
            "todo_title text, " +
            "todo_note text, " +
            "todo_date text, " +
            "todo_time text, " +
            "todo_repeat_week text, " +
            "todo_repeat_month text, " +
            "todo_color text, " +
            "todo_type integer, " +
            "todo_create_time text, " +
            "todo_update_time text," +
            "todo_tag text)";

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
