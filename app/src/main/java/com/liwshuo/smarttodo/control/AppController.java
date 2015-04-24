package com.liwshuo.smarttodo.control;

import android.app.Application;
import android.content.Context;

import com.liwshuo.smarttodo.data.DBManager;

/**
 * Created by shuo on 2015/4/16.
 */
public class AppController extends Application {
    private static Context context;
    private static final String TAG = AppController.class.getSimpleName();
    private static DBManager dbManager;


    @Override
    public void onCreate() {
        context = getApplicationContext();
        dbManager = new DBManager();
    }

    public static Context getContext() {
        return context;
    }

    public static synchronized DBManager getDbManager() {
        return dbManager;
    }
}
