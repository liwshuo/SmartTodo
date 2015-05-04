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
import com.liwshuo.smarttodo.control.LaterAdapter;
import com.liwshuo.smarttodo.data.DBManager;
import com.liwshuo.smarttodo.data.TodoMsg;
import com.liwshuo.smarttodo.utils.AppConfig;
import com.liwshuo.smarttodo.utils.LogUtil;

/**
 * 展示将来要处理的Todo
 * Created by liwshuo on 2015/4/19.
 */
public class LaterFragment extends Fragment implements SlideListViewWithoutVelocity.SlideListener,AdapterView.OnItemClickListener{
    private static final String TAG = LaterFragment.class.getSimpleName();
    private SlideListViewWithoutVelocity todoListView;
    private BaseAdapter laterAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_later, container, false);
        todoListView = (SlideListViewWithoutVelocity) view.findViewById(R.id.laterTodoList);
        laterAdapter = new LaterAdapter(AppController.getContext(), DBManager.getInstance().getLaterTodo(), CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        todoListView.setAdapter(laterAdapter);
        todoListView.setSlideListener(this);
        todoListView.setOnItemClickListener(this);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        notifyDataChanged();
    }

    /**
     * 设置不同滑动方式的处理方式
     * @param direction
     * @param i
     */
    @Override
    public void slideItem(SlideListViewWithoutVelocity.SlideDirection direction, int i) {
        if (direction == SlideListViewWithoutVelocity.SlideDirection.LEFTLEVELTWO) {
        } else if(direction == SlideListViewWithoutVelocity.SlideDirection.LEFTLEVELONE) {
        } else if (direction == SlideListViewWithoutVelocity.SlideDirection.RIGHTLEVELTWO) {
            DBManager.getInstance().switchType(laterAdapter.getTodoMsgId(i), AppConfig.TODO_DONE_TYPE);
            Toast.makeText(getActivity(),"任务完成", Toast.LENGTH_SHORT).show();
        } else if(direction == SlideListViewWithoutVelocity.SlideDirection.RIGHTLEVELONE){
            DBManager.getInstance().switchType(laterAdapter.getTodoMsgId(i), AppConfig.TODO_TODAY_TYPE);
            Toast.makeText(getActivity(),"今天就做", Toast.LENGTH_SHORT).show();
        }
        ((TodayFragment)getActivity().getSupportFragmentManager().getFragments().get(0)).notifyDataChanged();
        ((LaterFragment)getActivity().getSupportFragmentManager().getFragments().get(1)).notifyDataChanged();
        ((DoneFragment)getActivity().getSupportFragmentManager().getFragments().get(2)).notifyDataChanged();
    }

    /**
     * 刷新界面
     */
    public void notifyDataChanged() {
        laterAdapter.swapCursor(DBManager.getInstance().getLaterTodo());
        laterAdapter.notifyDataSetChanged();;
    }

    /**
     * 点击item后启动修改Todo信息的界面
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TodoMsg todoMsg = laterAdapter.getTodoMsg(position);
        TodoDetailActivity.actionStart(getActivity(), todoMsg, AppConfig.TODO_LATER_TYPE);
    }

}
