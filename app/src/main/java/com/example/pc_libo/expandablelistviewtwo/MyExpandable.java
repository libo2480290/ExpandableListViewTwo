package com.example.pc_libo.expandablelistviewtwo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * Created by pc_libo on 16/1/20.
 */
public class MyExpandable extends ExpandableListView implements AbsListView.OnScrollListener,ExpandableListView.OnGroupClickListener {
    public MyExpandable(Context context) {
        super(context);
        setOnScrollListener(this);
    }

    public MyExpandable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyExpandable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyExpandable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
