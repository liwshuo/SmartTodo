package com.liwshuo.smarttodo.activity;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.liwshuo.myview.FloatView;
import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.control.AppController;
import com.liwshuo.smarttodo.control.TodoFragmentPagerAdapter;
import com.liwshuo.smarttodo.service.ClipboardService;
import com.liwshuo.smarttodo.service.InitIntentService;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


/**
 * 用于承载三个Fragment的界面，
 */
public class TodoListActivity extends ActionBarActivity {
    private static final String TAG = TodoListActivity.class.getSimpleName();
    private ViewPager viewPager;
    private List<Fragment> fragmentList;  //保存三个fragment，作为参数传入TodoFragmentPagerAdapter中
    private FloatingActionButton buttonFloat;
    Fragment todayFragment;
    Fragment laterFragment;
    Fragment doneFragment;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.baymaxnotify);
          //  toolbar.setOnMenuItemClickListener(new ToolBarOnMenuItemClickListener());
        }
        buttonFloat = (FloatingActionButton) findViewById(R.id.buttonFloat);
        /**
         * 设置viewPager，将三个fragment放入viewPager中，并将TodayFragment设为默认
         */
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fragmentList = new ArrayList<Fragment>();
        todayFragment = new TodayFragment();
        laterFragment = new LaterFragment();
        doneFragment = new DoneFragment();
        fragmentList.add(laterFragment);
        fragmentList.add(todayFragment);
        fragmentList.add(doneFragment);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        TodoFragmentPagerAdapter todoFragmentPagerAdapter = new TodoFragmentPagerAdapter(fragmentManager, fragmentList);
        viewPager.setAdapter(todoFragmentPagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(new TodoOnPageChangeListener());
        buttonFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoAddActivity.actionStart(TodoListActivity.this);  //启动TodoListActivity
            }
        });
        InitIntentService.actionStart(getApplicationContext());

//        AppController.getInstance().createFloatView();
    }

    /**
     * viewPager中页面切换的监听器
     */
    public class TodoOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //      Animation animation = new TranslateAnimation()
            if (position == 0) {
                toolbar.setTitle(R.string.later_title);
            }else if (position == 1) {
                toolbar.setTitle(R.string.today_title);
            }else if(position == 2){
                toolbar.setTitle(R.string.done_title);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }



    /**
     * 在主界面里更新Fragment
     * @param requestCode
     * @param resultCode
     * @param data
     */
 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
         //   switch (requestCode) {
       //         case AppConfig.TODO_DONE_TYPE:
                    ((DoneFragment) doneFragment).notifyDataChanged();
      //              break;
      //          case AppConfig.TODO_TODAY_TYPE:
                    ((TodayFragment) todayFragment).notifyDataChanged();
     //               break;
     //           case AppConfig.TODO_LATER_TYPE:
                    ((LaterFragment) laterFragment).notifyDataChanged();
     //               break;
    //        }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
