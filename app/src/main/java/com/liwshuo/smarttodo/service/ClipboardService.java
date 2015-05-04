package com.liwshuo.smarttodo.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.liwshuo.myview.FloatView;
import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.activity.TodoListActivity;
import com.liwshuo.smarttodo.control.AppController;
import com.liwshuo.smarttodo.data.DBManager;
import com.liwshuo.smarttodo.data.TodoMsg;
import com.liwshuo.smarttodo.utils.LogUtil;
import com.liwshuo.smarttodo.utils.TimeUtils;


/**
 * 在该Service中捕获用户的复制操作，并讲用户的复制内容自动添加到Todo里
 * Created by shuo on 2015/4/16.
 */
public class ClipboardService extends Service {
    private final static String TAG = ClipboardService.class.getSimpleName();
    ClipboardManager clipboardManager;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ClipboardService.class);
        context.startService(intent);
    }
    public static void actionStop(Context context) {
        Intent intent = new Intent(context, ClipboardService.class);
        context.stopService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        clipboardManager  = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardListener());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class ClipboardListener implements ClipboardManager.OnPrimaryClipChangedListener {

        @Override
        public void onPrimaryClipChanged() {
            if (getSharedPreferences("SettingPreferences", MODE_PRIVATE).getBoolean("CopyListener", false)) {
                    FloatView floatView = AppController.getInstance().getFloatView();
                    if (floatView.getChildCount() > 0) {
                        floatView.create();
                    }else {
                        final MyEditText editText = new MyEditText(getApplicationContext());
        /*                ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setImageResource(R.drawable.float_visiable);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });*/
                        floatView.addView(editText);
                //        floatView.addView(imageView);
                        floatView.create();
                    }
            }
        }
    }

    class MyEditText extends EditText {

        public MyEditText(Context context) {
            super(context);
            setLayoutParams(new ViewGroup.LayoutParams(200, 40));
            setHint("Todo名称");
            setBackground(getResources().getDrawable(R.drawable.black_edittext));
            setSingleLine();
            setImeOptions(EditorInfo.IME_ACTION_DONE);
            setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    LogUtil.d(TAG, "actionId" + actionId);
                    LogUtil.d(TAG, "editorInfo" + EditorInfo.IME_ACTION_DONE);
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        //                                     && event.getAction() == KeyEvent.ACTION_DOWN) {
                        LogUtil.d(TAG, "enter!!!!!!!");
                        String title = getText().toString();
                        TodoMsg todoMsg = new TodoMsg();
                        todoMsg.setTodoTitle(title);
                        todoMsg.setTodoNote(clipboardManager.getPrimaryClip().getItemAt(0).getText().toString());
                        todoMsg.setTodoType(1);
                        todoMsg.setTodoDate(new TimeUtils().getCurrentDate());
                        todoMsg.setTodoTime(new TimeUtils().getCurrentTimeWithoutSecond());
                        DBManager.getInstance().addTodo(todoMsg);
                        setText("");
                        FloatView floatView = AppController.getInstance().getFloatView();
                        floatView.destroy();
                    }
                    return true;
                }
            });
        }
        @Override
        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK &&
                    event.getAction() == KeyEvent.ACTION_UP) {
                // do your stuff
                setText("");
                FloatView floatView = AppController.getInstance().getFloatView();
                floatView.destroy();
                LogUtil.d(TAG, "lalala");
                return false;
            }
            return super.dispatchKeyEvent(event);
        }
    }
}
