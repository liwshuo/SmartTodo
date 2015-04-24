package com.liwshuo.smarttodo.activity;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.control.TodoFragmentPagerAdapter;
import com.liwshuo.smarttodo.service.ClipboardService;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class TodoListActivity extends ActionBarActivity {
    private static final String TAG = TodoListActivity.class.getSimpleName();
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
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
                TodoAddActivity.actionStart(TodoListActivity.this);
            }
        });
        ClipboardService.actionStart(this);
    }

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

    @Override
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
    }
}
