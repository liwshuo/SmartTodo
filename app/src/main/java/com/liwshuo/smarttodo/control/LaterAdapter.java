package com.liwshuo.smarttodo.control;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.data.TodoMsg;
import com.liwshuo.smarttodo.utils.AppConfig;
import com.liwshuo.smarttodo.utils.LogUtil;


/**
 * Created by shuo on 2015/4/16.
 */
public class LaterAdapter extends BaseAdapter {

    public LaterAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View view = LayoutInflater.from(context).inflate(R.layout.later_item, null);
        viewHolder.laterTodoView = (TextView) view.findViewById(R.id.laterTodoView);
        viewHolder.titleIndex = cursor.getColumnIndexOrThrow(AppConfig.TODO_TITLE_COLUMN);
        viewHolder.laterTodoTime = (TextView) view.findViewById(R.id.laterTimeView);
        viewHolder.timeIndex = cursor.getColumnIndexOrThrow(AppConfig.TODO_TIME_COLUMN);
        viewHolder.laterTodoDate = (TextView) view.findViewById(R.id.laterDateView);
        viewHolder.dateIndex = cursor.getColumnIndexOrThrow(AppConfig.TODO_DATE_COLUMN);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.laterTodoView.setText(cursor.getString(viewHolder.titleIndex));
        viewHolder.laterTodoTime.setText(cursor.getString(viewHolder.timeIndex));
        viewHolder.laterTodoDate.setText(cursor.getString(viewHolder.dateIndex));
    }

    protected class ViewHolder{
        int titleIndex;
        int dateIndex;
        int timeIndex;
        TextView laterTodoView;
        TextView laterTodoTime;
        TextView laterTodoDate;
    }
}
