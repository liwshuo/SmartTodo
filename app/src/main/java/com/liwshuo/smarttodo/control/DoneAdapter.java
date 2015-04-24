package com.liwshuo.smarttodo.control;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
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
public class DoneAdapter extends BaseAdapter {

    public DoneAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View view = LayoutInflater.from(context).inflate(R.layout.done_item, null);
        viewHolder.doneTodoView = (TextView) view.findViewById(R.id.doneTodoView);
        viewHolder.titleIndex = cursor.getColumnIndexOrThrow(AppConfig.TODO_TITLE_COLUMN);
        viewHolder.doneTodoTime = (TextView) view.findViewById(R.id.doneTimeView);
        viewHolder.timeIndex = cursor.getColumnIndexOrThrow(AppConfig.TODO_TIME_COLUMN);
        viewHolder.doneTodoDate = (TextView) view.findViewById(R.id.doneDateView);
        viewHolder.dateIndex = cursor.getColumnIndexOrThrow(AppConfig.TODO_DATE_COLUMN);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.doneTodoView.setText(cursor.getString(viewHolder.titleIndex));
        viewHolder.doneTodoView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        viewHolder.doneTodoTime.setText(cursor.getString(viewHolder.timeIndex));
        viewHolder.doneTodoDate.setText(cursor.getString(viewHolder.dateIndex));
    }

    protected class ViewHolder{
        int titleIndex;
        int dateIndex;
        int timeIndex;
        TextView doneTodoView;
        TextView doneTodoTime;
        TextView doneTodoDate;
    }
}
