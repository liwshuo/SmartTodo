package com.liwshuo.smarttodo.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liwshuo.smarttodo.control.AppController;
import com.liwshuo.smarttodo.service.AlarmIntentService;
import com.liwshuo.smarttodo.utils.AppConfig;
import com.liwshuo.smarttodo.utils.LogUtil;
import com.liwshuo.smarttodo.utils.TimeUtils;


/**
 * 提供了对数据库的一系列操作
 * Created by shuo on 2015/4/16.
 */
public class DBManager {
    private final String TAG = DBManager.class.getSimpleName();
    private SQLiteDatabase db;
    private static DBManager dbManager;

    /**
     * 初始化数据库
     */
    public DBManager() {
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(AppController.getContext(), AppConfig.DB_NAME, null, AppConfig.DB_VERSION);
        db = mySQLiteOpenHelper.getWritableDatabase();
    }

    /**
     * 使用单例模式提供DBManager对象
     * @return
     */
    public synchronized static DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }


    /**
     * 添加Todo
     * @param todoMsg
     */
    public void addTodo(TodoMsg todoMsg) {
        ContentValues contentValues = getContentValues(todoMsg);
        db.insert(AppConfig.TABLE_NAME, null, contentValues);
        AppConfig.updateWidget(AppController.getContext());
        AlarmIntentService.actionStart(AppController.getContext(), todoMsg);
    }

    /**
     * 更新Todo
     * @param todoMsg
     */
    public void updateTodo(TodoMsg todoMsg) {
        ContentValues contentValues = getContentValues(todoMsg);
        String[] whereClauses = {String.valueOf(todoMsg.get_id())};
        db.update(AppConfig.TABLE_NAME, contentValues, "_id = ?", whereClauses);
        AppConfig.updateWidget(AppController.getContext());
        AlarmIntentService.actionStart(AppController.getContext(), todoMsg);
    }

    /**
     * 创建Todo的ContentValue
     * @param todoMsg
     * @return
     */
    private ContentValues getContentValues(TodoMsg todoMsg) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppConfig.TODO_TITLE_COLUMN, todoMsg.getTodoTitle());
        contentValues.put(AppConfig.TODO_NOTE_COLUMN, todoMsg.getTodoNote());
        contentValues.put(AppConfig.TODO_DATE_COLUMN, todoMsg.getTodoDate());
        contentValues.put(AppConfig.TODO_TIME_COLUMN, todoMsg.getTodoTime());
        contentValues.put(AppConfig.TODO_REPEAT_WEEK_COLUMN, todoMsg.getTodoRepeatWeek());
        contentValues.put(AppConfig.TODO_REPEAT_MONTH_COLUMN, todoMsg.getTodoRepeatMonth());
        contentValues.put(AppConfig.TODO_COLOR_COLUMN, todoMsg.getTodoColor());
        contentValues.put(AppConfig.TODO_TYPE_COLUMN, todoMsg.getTodoType());
        contentValues.put(AppConfig.TODO_CREATE_TIME_COLUMN, todoMsg.getTodoCreateTime());
        contentValues.put(AppConfig.TODO_UPDATE_TIME_COLUMN, todoMsg.getTodoUpdateTime());
        contentValues.put(AppConfig.TODO_TAG_COLUMN, todoMsg.getTodoTag());
        return contentValues;
    }

    /**
     * 切换Todo的类型
     * @param _id
     * @param todoType
     */
    public void switchType(int _id, int todoType) {
        ContentValues contentValues = new ContentValues();
        switch (todoType) {
            case AppConfig.TODO_LATER_TYPE:
                contentValues.put(AppConfig.TODO_TYPE_COLUMN, AppConfig.TODO_LATER_TYPE);
                break;
            case AppConfig.TODO_TODAY_TYPE:
                contentValues.put(AppConfig.TODO_TYPE_COLUMN, AppConfig.TODO_TODAY_TYPE);
                contentValues.put(AppConfig.TODO_DATE_COLUMN, new TimeUtils().getCurrentDate());
                break;
            case AppConfig.TODO_DONE_TYPE:
                contentValues.put(AppConfig.TODO_TYPE_COLUMN, AppConfig.TODO_DONE_TYPE);
                break;
            default:
                break;
        }
        db.update(AppConfig.TABLE_NAME, contentValues, "_id=?", new String[]{String.valueOf(_id)});
        AppConfig.updateWidget(AppController.getContext());
    }

    /**
     * 获取所有的Todo的Cursor
     * @return
     */
    public Cursor getAllTodo() {
        return db.query(AppConfig.TABLE_NAME, null, null, null, null, null, null);
    }

    /**
     * 获取今天的Todo的Cursor
     * @return
     */
    public Cursor getTodayTodo() {
        return db.query(AppConfig.TABLE_NAME, null, AppConfig.TODO_TYPE_COLUMN + " = ?", new String[]{"1"}, null, null, null, null);
    }

    /**
     * 获取包含在widget上显示的Todo， todo类型为未完成的当天todo和已完成的且更新时间为当天的todo
     * @return
     */
    public Cursor getTodayTodoContainsUndone() {
        LogUtil.d(TAG, new TimeUtils().getCurrentDate());
        String whereClause = AppConfig.TODO_TYPE_COLUMN + " = ? or (" + AppConfig.TODO_UPDATE_DATE_COLUMN + " = ? and " + AppConfig.TODO_TYPE_COLUMN + " = ?)";
        String[] whereArgs =  new String[]{String.valueOf(AppConfig.TODO_TODAY_TYPE), new TimeUtils().getCurrentDate(), String.valueOf(AppConfig.TODO_DONE_TYPE)};
        Cursor cursor = db.query(AppConfig.TABLE_NAME, null, whereClause,
              whereArgs, null, null, AppConfig.TODO_TYPE_COLUMN +" desc", null);
        LogUtil.d(TAG, "count"+cursor.getCount());
        return cursor;
    }

    /**
     * 获取已经完成的Todo的Cursor
     * @return
     */
    public Cursor getDoneTodo() {
        return db.query(AppConfig.TABLE_NAME, null, AppConfig.TODO_TYPE_COLUMN + " = ?", new String[]{"0"}, null, null, null, null);
    }

    public Cursor getUndoneTodo() {
        return db.query(AppConfig.TABLE_NAME, null, AppConfig.TODO_TYPE_COLUMN + " = ? or " + AppConfig.TODO_TYPE_COLUMN + " = ?", new String[]{"1", "2"}, null, null, null, null);
    }

    /**
     * 获取将来的Todo的Cursor
     * @return
     */
    public Cursor getLaterTodo() {
        return db.query(AppConfig.TABLE_NAME, null, AppConfig.TODO_TYPE_COLUMN + " = ?", new String[]{"2"}, null, null, null, null);
    }

    /**
     * 删除Todo
     */
    public void deleteTodo(int id) {
        db.delete(AppConfig.TABLE_NAME, AppConfig.TODO_ID_COLUMN + " = ?", new String[]{String.valueOf(id)});
        AppConfig.updateWidget(AppController.getContext());
    }

    /**
     * 获取Todo的类型
     * @param id
     * @return
     */
    public int getTodoType(int id) {
        Cursor cursor = db.query(AppConfig.TABLE_NAME, null, AppConfig.TODO_ID_COLUMN + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(AppConfig.TODO_TYPE_COLUMN));
    }

    /**
     * 设置Todo为已完成
     * @param id
     */
    public void setTodoDone(int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppConfig.TODO_TYPE_COLUMN, 0);
        contentValues.put(AppConfig.TODO_UPDATE_DATE_COLUMN, new TimeUtils().getCurrentDate());
        String[] args = {String.valueOf(id)};
        db.update(AppConfig.TABLE_NAME, contentValues, AppConfig.TODO_ID_COLUMN + " = ?", args);
        AppConfig.updateWidget(AppController.getContext());
    }

    /**
     * 设置Todo为当天未完成
     * @param id
     */
    public void setTodoUndone(int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppConfig.TODO_TYPE_COLUMN, 1);
        contentValues.put(AppConfig.TODO_UPDATE_DATE_COLUMN, new TimeUtils().getCurrentDate());
        String[] args = {String.valueOf(id)};
        db.update(AppConfig.TABLE_NAME, contentValues, AppConfig.TODO_ID_COLUMN + " = ?", args);
        AppConfig.updateWidget(AppController.getContext());
    }

    public void closeDB() {
        db.close();
    }

}
