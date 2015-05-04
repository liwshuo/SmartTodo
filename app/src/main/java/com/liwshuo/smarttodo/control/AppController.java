package com.liwshuo.smarttodo.control;

import android.app.Application;
import android.content.Context;

import com.liwshuo.myview.FloatView;


/**
 * 提供全局变量
 * Created by shuo on 2015/4/16.
 */
public class AppController extends Application {
    private static Context context;
    private static final String TAG = AppController.class.getSimpleName();
    private FloatView floatView = null;
    private static AppController appController = null;


    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static synchronized AppController getInstance() {
        if (appController == null) {
            appController = new AppController();
        }
        return appController;
    }

    public synchronized FloatView getFloatView() {
        if(floatView == null){
            floatView = new FloatView(context);
        }
        return floatView;
    }

    public void createFloatView() {
        if(floatView == null){
            floatView = new FloatView(context);
        }

        floatView.create();
    }

    public void destroyFloatView() {
        if (floatView != null) {
            floatView.destroy();
        }
    }
}
