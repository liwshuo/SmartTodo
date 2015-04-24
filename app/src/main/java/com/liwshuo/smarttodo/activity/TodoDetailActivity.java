package com.liwshuo.smarttodo.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.data.DBManager;
import com.liwshuo.smarttodo.data.TodoMsg;
import com.liwshuo.smarttodo.service.AlarmIntentService;
import com.liwshuo.smarttodo.utils.AppConfig;
import com.liwshuo.smarttodo.utils.LogUtil;
import com.liwshuo.smarttodo.utils.TimeUtils;

public class TodoDetailActivity extends Activity implements View.OnClickListener{
    private final static String TAG = TodoDetailActivity.class.getSimpleName();
    private EditText todoTitleText;
    private TextView todoDateText;
    private TextView todoRepeatText;
    private TextView todoTagText;
    private EditText todoNoteText;
    private TextView confirmTodo;
    private TodoMsg todoMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        todoTitleText = (EditText) findViewById(R.id.todoTitleText);
        todoDateText = (TextView) findViewById(R.id.todoDateText);
        todoRepeatText = (TextView) findViewById(R.id.todoRepeatText);
        todoTagText = (TextView) findViewById(R.id.todoTagText);
        todoNoteText = (EditText) findViewById(R.id.todoNoteText);
        confirmTodo = (TextView) findViewById(R.id.confirmTodo);
        confirmTodo.setOnClickListener(this);
        todoDateText.setOnClickListener(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        todoMsg = (TodoMsg) bundle.getParcelable("todoMsg");
        if(!TextUtils.isEmpty(todoMsg.getTodoTitle())){
            todoTitleText.setText(todoMsg.getTodoTitle());
        }
        if(!TextUtils.isEmpty(todoMsg.getTodoDate()) && !TextUtils.isEmpty(todoMsg.getTodoTime())) {
            todoDateText.setText(todoMsg.getTodoDate() + " " + todoMsg.getTodoTime());
        }
        if(!TextUtils.isEmpty(todoMsg.getTodoRepeatMonth())){
            todoTitleText.setText(todoMsg.getTodoRepeatMonth());
        }
        if(!TextUtils.isEmpty(todoMsg.getTodoRepeatWeek())){
            todoTitleText.setText(todoMsg.getTodoRepeatWeek());
        }
        if(!TextUtils.isEmpty(todoMsg.getTodoTag())){
            todoTagText.setText(todoMsg.getTodoTag());
        }
        if(!TextUtils.isEmpty(todoMsg.getTodoNote())){
            todoNoteText.setText(todoMsg.getTodoNote());
        }
    }

    public static void actionStart(Context context, TodoMsg todoMsg, int requestCode) {
        Intent intent = new Intent(context, TodoDetailActivity.class);
        Bundle bundle = new Bundle();
        LogUtil.d(TAG, todoMsg.getTodoTitle());
        bundle.putParcelable("todoMsg", todoMsg);
        intent.putExtras(bundle);
        ((TodoListActivity)context).startActivityForResult(intent, requestCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.todoDateText: {
                TimePickerActivity.actionStartForActivity(this,todoDateText.getText().toString(),1);
            }
            break;
            case R.id.confirmTodo: {
                generateTodoMsg();
                DBManager.getInstance().updateTodo(todoMsg);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                LogUtil.d(TAG,"confirmTodo");
                if(!TextUtils.isEmpty(todoDateText.getText().toString())) {
                    AlarmIntentService.actionStart(getApplicationContext(), todoMsg.getTodoDate(), todoMsg.getTodoTime(), null, todoMsg.getTodoTitle());
                }
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1: {
                    Bundle bundle = data.getExtras();
                    todoDateText.setText(bundle.getString("dateAndTime"));
                }
            }
        }
    }

    private void generateTodoMsg() {
        String todoTitle = todoTitleText.getText().toString();
        String todoNote = todoNoteText.getText().toString();
        String todoRepeat = todoRepeatText.getText().toString();
        String todoTag = todoTagText.getText().toString();
        String todoDate = todoDateText.getText().toString();
        todoMsg.setTodoTitle(todoTitle);
        todoMsg.setTodoNote(todoNote);
        if (!TextUtils.isEmpty(todoDate)) {
            todoMsg.setTodoDate(todoDate.split(" ")[0]);
            todoMsg.setTodoTime(todoDate.split(" ")[1]);
            if(todoDate.split(" ")[0].compareTo(new TimeUtils().getCurrentDate()) > 0){
                todoMsg.setTodoType(2);
            }else {
                todoMsg.setTodoType(1);
            }
        }else {
            todoMsg.setTodoType(1);
        }
        todoMsg.setTodoRepeatWeek(todoRepeat);
        todoMsg.setTodoRepeatMonth(todoRepeat);
        todoMsg.setTodoTag(todoTag);
        todoMsg.setTodoUpdateTime(new TimeUtils().getCurrentDateAndTime());
    }

}