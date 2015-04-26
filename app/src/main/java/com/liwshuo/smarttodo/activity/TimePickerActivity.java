package com.liwshuo.smarttodo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.utils.TimeUtils;

import java.util.Calendar;

/**
 * 用于展示TimePicker控件的Activity
 */
public class TimePickerActivity extends Activity implements View.OnClickListener {

    private TextView datePickerView;
    private TimePicker timePicker;
    private TextView confirmTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        datePickerView = (TextView) findViewById(R.id.datePickerText);
        datePickerView.setOnClickListener(this);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        confirmTime = (TextView) findViewById(R.id.confirmTime);
        confirmTime.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        String dateAndTime = bundle.getString("dateAndTime");
        if(!TextUtils.isEmpty(dateAndTime)) {
            String[] dateAndTimeArray = dateAndTime.split(" ");
            String date = dateAndTimeArray[0];
            String time = dateAndTimeArray[1];
            datePickerView.setText(date);
            String[] timeArray = time.split(":");
            timePicker.setCurrentHour(Integer.parseInt(timeArray[0]));
            timePicker.setCurrentMinute(Integer.parseInt(timeArray[1]));
        }else {
            datePickerView.setText(new TimeUtils().getCurrentDate());
            timePicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(Calendar.getInstance().get(Calendar.MINUTE));
        }

    }

    /**
     * 方便其他组件启动该Activity
     * @param context
     * @param dateAndTime
     * @param requestCode
     */
    public static void actionStartForActivity(Context context, String dateAndTime, int requestCode) {
        Intent intent = new Intent(context, TimePickerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("dateAndTime", dateAndTime);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 将日期和时间返回给启动该Activity的组件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.datePickerText:
                DatePickerActivity.actionStartForActivity(this, datePickerView.getText().toString(), 1);
                break;
            case R.id.confirmTime: {
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("dateAndTime", datePickerView.getText().toString() + " " +
                        (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute));
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:{
                    Bundle bundle = data.getExtras();
                    datePickerView.setText(bundle.getString("date"));
                }
                break;
            }
        }
    }
}
