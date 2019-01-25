package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mhy on 2018/1/17.
 */

public class AutoHeightViewPager extends ViewPager {

    public AutoHeightViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;

// 下面遍历所有child的高度

        for (int i = 0; i < getChildCount(); i++) {

            View child = getChildAt(i);

            child.measure(widthMeasureSpec,

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            int h = child.getMeasuredHeight();

// 采用最大的view的高度

            if (h > height) {

                height = h;

            }

        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,

                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

}