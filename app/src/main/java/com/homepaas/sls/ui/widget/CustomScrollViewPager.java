package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.homepaas.sls.R;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
public class CustomScrollViewPager extends ViewPager {

    private boolean canScroll = false;

    public CustomScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomScrollViewPager);
        canScroll = a.getBoolean(R.styleable.CustomScrollViewPager_scrollable, false);
        a.recycle();
    }

    public void setScrollable(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canScroll && super.onInterceptTouchEvent(ev);
    }
}
