package com.liwshuo.smarttodo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.liwshuo.myview.SlideListViewWithoutVelocity;
import com.liwshuo.smarttodo.R;
import com.liwshuo.smarttodo.control.AppController;
import com.liwshuo.smarttodo.control.BaseAdapter;
import com.liwshuo.smarttodo.control.DoneAdapter;
import com.liwshuo.smarttodo.data.DBManager;
import com.liwshuo.smarttodo.data.TodoMsg;
import com.liwshuo.smarttodo.utils.AppConfig;
import com.liwshuo.smarttodo.utils.LogUtil;

/**
 * Created by liwshuo on 2015/4/19.
 */
public class DoneFragment extends Fragment implements SlideListViewWithoutVelocity.SlideListener,AdapterView.OnItemClickListener {
    private static final String TAG = DoneFragment.class.getSimpleName();
    private SlideListViewWithoutVelocity todoListView;
    private BaseAdapter doneAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done, container, false);
        todoListView = (SlideListViewWithoutVelocity) view.findViewById(R.id.doneTodoList);
        doneAdapter = new DoneAdapter(AppController.getContext(), AppController.getDbManager().getDoneTodo(), CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        todoListView.setAdapter(doneAdapter);
        todoListView.setSlideListener(this);
        todoListView.setOnItemClickListener(this);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        notifyDataChanged();
    }
    @Override
    public void slideItem(SlideListViewWithoutVelocity.SlideDirection direction, int i) {
        if (direction == SlideListViewWithoutVelocity.SlideDirection.LEFTLEVELTWO) {
            DBManager.getInstance().switchType(doneAdapter.getTodoMsgId(i), AppConfig.TODO_LATER_TYPE);
            Toast.makeText(getActivity(), "以后再说", Toast.LENGTH_SHORT).show();
        } else if(direction == SlideListViewWithoutVelocity.SlideDirection.LEFTLEVELONE) {
            DBManager.getInstance().switchType(doneAdapter.getTodoMsgId(i), AppConfig.TODO_TODAY_TYPE);
            Toast.makeText(getActivity(),"今天就做", Toast.LENGTH_SHORT).show();
        } else if (direction == SlideListViewWithoutVelocity.SlideDirection.RIGHTLEVELTWO) {
        } else if(direction == SlideListViewWithoutVelocity.SlideDirection.RIGHTLEVELONE){
        }
        ((TodayFragment)getActivity().getSupportFragmentManager().getFragments().get(0)).notifyDataChanged();
        ((LaterFragment)getActivity().getSupportFragmentManager().getFragments().get(1)).notifyDataChanged();
        ((DoneFragment)getActivity().getSupportFragmentManager().getFragments().get(2)).notifyDataChanged();
    }

    public void notifyDataChanged() {
        doneAdapter.swapCursor(DBManager.getInstance().getDoneTodo());
        doneAdapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TodoMsg todoMsg = doneAdapter.getTodoMsg(position);
        TodoDetailActivity.actionStart(getActivity(), todoMsg, AppConfig.TODO_DONE_TYPE);
    }

}
