package com.liwshuo.smarttodo.control;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.utils.AppConfig;


/**
 * Created by shuo on 2015/4/16.
 */
public class TodayAdapter extends BaseAdapter {

    public TodayAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View view = LayoutInflater.from(context).inflate(R.layout.today_item, null);
        viewHolder.todayTodoView = (TextView) view.findViewById(R.id.todayTodoView);
        viewHolder.titleIndex = cursor.getColumnIndexOrThrow(AppConfig.TODO_TITLE_COLUMN);
        viewHolder.todayTodoTime = (TextView) view.findViewById(R.id.todayTimeView);
        viewHolder.timeIndex = cursor.getColumnIndexOrThrow(AppConfig.TODO_TIME_COLUMN);
        viewHolder.todayTodoDate = (TextView) view.findViewById(R.id.todayDateView);
        viewHolder.dateIndex = cursor.getColumnIndexOrThrow(AppConfig.TODO_DATE_COLUMN);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.todayTodoView.setText(cursor.getString(viewHolder.titleIndex));
        viewHolder.todayTodoTime.setText(cursor.getString(viewHolder.timeIndex));
        viewHolder.todayTodoDate.setText(cursor.getString(viewHolder.dateIndex));
    }

    protected class ViewHolder{
        int titleIndex;
        int dateIndex;
        int timeIndex;
        TextView todayTodoView;
        TextView todayTodoTime;
        TextView todayTodoDate;
    }

}
