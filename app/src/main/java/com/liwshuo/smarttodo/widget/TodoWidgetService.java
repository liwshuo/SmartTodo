package com.liwshuo.smarttodo.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by liwshuo on 2015/5/10.
 */
public class TodoWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TodoListFactory(getApplicationContext(), intent);
    }
}
