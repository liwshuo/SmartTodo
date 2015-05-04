package com.liwshuo.smarttodo.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;

import com.liwshuo.smarttodo.R;

public class SettingsActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private SwitchPreference switchPreference;
    private Switch floatWindowSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.baymaxnotify);
            //  toolbar.setOnMenuItemClickListener(new ToolBarOnMenuItemClickListener());
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.replace(R.id.settingsFragment, settingsFragment);
        fragmentTransaction.commit();
    }

}
