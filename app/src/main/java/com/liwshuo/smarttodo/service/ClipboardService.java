package com.liwshuo.smarttodo.service;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

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
                    floatView.setGravity(FloatView.VERTICAL);
                    if (floatView.getChildCount() > 0) {
                        ((EditText) floatView.getChildAt(0)).setText("");
                        ((TextView) floatView.getChildAt(1)).setText(new TimeUtils().getCurrentDate());
                        ((TextView) floatView.getChildAt(2)).setText(new TimeUtils().getCurrentTimeWithoutSecond());
                        floatView.create();
                    }else {
                        final MyEditText editText = new MyEditText(getApplicationContext());
                        final TextView dateView = new TextView(getApplicationContext());
                        dateView.setGravity(Gravity.CENTER);
                  //      dateView.setLayoutParams(new ViewGroup.LayoutParams(200, 40));
                        dateView.setBackground(getResources().getDrawable(R.drawable.black_edittext));
                        dateView.setText(new TimeUtils().getCurrentDate());
                        final String[] dateArray = dateView.getText().toString().split("-");
                        final int year = Integer.parseInt(dateArray[0]);
                        final int month = Integer.parseInt(dateArray[1]) - 1;
                        final int day = Integer.parseInt(dateArray[2]);
                        dateView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        monthOfYear = monthOfYear + 1;
                                        dateView.setText(year + "-" + (monthOfYear < 10 ? "0" + monthOfYear : monthOfYear) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth));
                                    }
                                }, year, month, day);
                                datePickerDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                datePickerDialog.show();
                            }
                        });
                        final TextView timeView = new TextView(getApplicationContext());
                        timeView.setBackground(getResources().getDrawable(R.drawable.black_edittext));
                        timeView.setText(new TimeUtils().getCurrentTimeWithoutSecond());
                        final String[] timeArray = timeView.getText().toString().split(":");
                        final int hour = Integer.parseInt(timeArray[0]);
                        final int minute = Integer.parseInt(timeArray[1]);
                        timeView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TimePickerDialog timePickerDialog = new TimePickerDialog(getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        timeView.setText((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + (minute < 10 ? "0" + minute : minute));
                                    }
                                }, hour, minute, true);
                                timePickerDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                timePickerDialog.show();
                            }
                        });
                        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                LogUtil.d(TAG, "actionId" + actionId);
                                LogUtil.d(TAG, "editorInfo" + EditorInfo.IME_ACTION_DONE);
                                if (actionId == EditorInfo.IME_ACTION_DONE) {
                                    //                                     && event.getAction() == KeyEvent.ACTION_DOWN) {
                                    LogUtil.d(TAG, "enter!!!!!!!");
                                    String title = editText.getText().toString();
                                    String date = dateView.getText().toString();
                                    String time = timeView.getText().toString();
                                    TodoMsg todoMsg = new TodoMsg();
                                    todoMsg.setTodoTitle(title);
                                    todoMsg.setTodoNote(clipboardManager.getPrimaryClip().getItemAt(0).getText().toString());
                                    todoMsg.setTodoType(1);
                                    todoMsg.setTodoDate(date);
                                    todoMsg.setTodoTime(time);
                                    DBManager.getInstance().addTodo(todoMsg);
                                    editText.setText("");
                                    FloatView floatView = AppController.getInstance().getFloatView();
                                    floatView.destroy();
                                    AlarmIntentService.actionStart(getApplicationContext(), date, time, null, title);
                                }
                                return true;
                            }
                        });
        /*                ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setImageResource(R.drawable.float_visiable);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });*/
                        floatView.addView(editText);
                        floatView.addView(dateView);
                        floatView.addView(timeView);
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
            setTextColor(Color.WHITE);
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
