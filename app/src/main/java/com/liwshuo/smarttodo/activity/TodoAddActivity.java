package com.liwshuo.smarttodo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.data.DBManager;
import com.liwshuo.smarttodo.data.TodoMsg;
import com.liwshuo.smarttodo.utils.TimeUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 添加新Todo的界面
 */
public class TodoAddActivity extends ActionBarActivity implements View.OnClickListener, EditText.OnEditorActionListener{

    private EditText newTodo;
    private TextView newTodoDate;
    private ImageButton newTodoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_add);
        newTodo = (EditText) findViewById(R.id.newTodo);
        newTodoDate = (TextView) findViewById(R.id.newTodoDate);
        newTodoButton = (ImageButton) findViewById(R.id.newTodoButton);
        newTodoDate.setText(new TimeUtils().getCurrentDateAndTimeWithoutSecond());
        newTodoDate.setOnClickListener(this);
        newTodo.setOnEditorActionListener(this);
        newTodoButton.setOnClickListener(this);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputManager = (InputMethodManager) newTodo.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(newTodo, 0);
            }

        }, 500);
    }

    /**
     * 方便其他组件启动该Activity,只需在其他组件里调用TodoDetailActivity.actionStart(context)即可
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TodoAddActivity.class);
        ((Activity)context).startActivityForResult(intent, 1);
    }

    /**
     * 点击响应操作
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newTodoDate:
                TimePickerActivity.actionStartForActivity(this, newTodoDate.getText().toString(), 1);
                break;
            case R.id.newTodoButton:{
                if(!TextUtils.isEmpty(newTodo.getText().toString())) {
                    DBManager.getInstance().addTodo(generateTodoMsg());
                    finish();
                }else{
                    finish();

                }
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            finish();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1: {
                    Bundle bundle = data.getExtras();
                    newTodoDate.setText(bundle.getString("dateAndTime"));
                }
            }
        }
    }

    private TodoMsg generateTodoMsg() {
        TodoMsg todoMsg = new TodoMsg();
        String todoTitle = newTodo.getText().toString();
        String todoDate = newTodoDate.getText().toString();
        todoMsg.setTodoTitle(todoTitle);
        if (!TextUtils.isEmpty(todoDate)) {
            todoMsg.setTodoDate(todoDate.split(" ")[0]);
            todoMsg.setTodoTime(todoDate.split(" ")[1]);
        }
        todoMsg.setTodoCreateTime(new TimeUtils().getCurrentDateAndTime());
        todoMsg.setTodoType(1);
        return todoMsg;
    }
}
