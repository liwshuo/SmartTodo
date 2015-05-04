package com.liwshuo.smarttodo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liwshuo.myview.FloatView;
import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.control.AppController;
import com.liwshuo.smarttodo.service.ClipboardService;
import com.liwshuo.smarttodo.utils.LogUtil;

import java.util.zip.Inflater;

/**
 * Created by liwshuo on 2015/5/4.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final static String TAG = SettingsFragment.class.getSimpleName();
 //   private CheckBoxPreference floatWindowPreference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getPreferenceManager().setSharedPreferencesName("SettingPreferences");
        addPreferencesFromResource(R.xml.fragment_settings);
   //     floatWindowPreference = (CheckBoxPreference) findPreference("FloatWindow");

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "CopyListener":{
                if (sharedPreferences.getBoolean(key, false)) {
              //      floatWindowPreference.setSelectable(true);
                    ClipboardService.actionStart(getActivity());
                }else {
             //       floatWindowPreference.setSelectable(false);
             //       floatWindowPreference.setChecked(false);
                    ClipboardService.actionStop(getActivity());
                }
            }
            break;
        /*    case "FloatWindow":{
                if (sharedPreferences.getBoolean(key, false)) {
                    ImageView imageView = new ImageView(AppController.getContext());
                    imageView.setImageResource(R.drawable.float_hide);
                    FloatView floatView = AppController.getInstance().getFloatView();
                    if (floatView.getChildCount() == 0) {
                        LogUtil.d(TAG, floatView.getChildCount() + "true");
                        floatView.addView(imageView);
                        floatView.create();
                    } else {
                        floatView.create();
                    }
                } else {
                    FloatView floatView = AppController.getInstance().getFloatView();
                    if (floatView.getChildCount() > 0) {
                        LogUtil.d(TAG, floatView.getChildCount() + "false");
                        floatView.destroy();
                    }
                }
            }
            break;*/
        }

    }
}
