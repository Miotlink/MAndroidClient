package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * on 2016/4/14 0014
 *
 * @author zhudongjie .
 */
public class UnTouchedRecyclerView extends RecyclerView{

    public UnTouchedRecyclerView(Context context) {
        this(context, null);
    }

    public UnTouchedRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnTouchedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
