package com.liwshuo.smarttodo.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

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
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = getSharedPreferences("SettingPreferences", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("CopyListener", false)){
            ClipboardService.actionStart(this);
        }else {
            ClipboardService.actionStop(this);
        }
    }
}
