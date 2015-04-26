package com.liwshuo.smarttodo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.liwshuo.smarttodo.R;

import java.util.Calendar;

/**
 * 用于展示DatePicker控件的Activity
 */
public class DatePickerActivity extends Activity implements View.OnClickListener {

    private DatePicker datePicker;
    private TextView confirmDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        confirmDate = (TextView) findViewById(R.id.confirmDate);
        confirmDate.setOnClickListener(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String date = bundle.getString("date");
        if (!TextUtils.isEmpty(date)) {
            String[] dateArray = date.split("-");
            datePicker.updateDate(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]) - 1, Integer.parseInt(dateArray[2]));
        }else {
            datePicker.updateDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        }
    }

    /**
     * 方便其他组件启动该Activity
     * @param context
     * @param date
     * @param requestCode
     */
    public static void actionStartForActivity(Context context, String date, int requestCode) {
        Intent intent = new Intent(context, DatePickerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 将日期返回给启动该Activity的组件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmDate: {
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("date", year + "-" +
                        (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day));
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
