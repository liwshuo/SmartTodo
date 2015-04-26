package com.liwshuo.smarttodo.control;

import android.app.Application;
import android.content.Context;

import com.liwshuo.smarttodo.data.DBManager;

/**
 * 提供全局变量
 * Created by shuo on 2015/4/16.
 */
public class AppController extends Application {
    private static Context context;
    private static final String TAG = AppController.class.getSimpleName();


    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
