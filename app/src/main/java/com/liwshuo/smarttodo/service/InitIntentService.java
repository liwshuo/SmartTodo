package com.liwshuo.smarttodo.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import com.liwshuo.smarttodo.utils.LogUtil;

/**
 * Created by liwshuo on 2015/5/4.
 */
public class InitIntentService extends IntentService {
    private final static String TAG = InitIntentService.class.getSimpleName();

    public InitIntentService() {
        super(TAG);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, InitIntentService.class);
        LogUtil.d(TAG, "actionStart");
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.d(TAG, "initService");
        SharedPreferences sharedPreferences = getSharedPreferences("SettingPreferences", MODE_PRIVATE);
        LogUtil.d(TAG, "initService");
        if(sharedPreferences.getBoolean("CopyListener", false)){
            LogUtil.d(TAG, "true");
            ClipboardService.actionStart(this);
        }else {
            LogUtil.d(TAG, "false");
            ClipboardService.actionStop(this);
        }
    }
}
