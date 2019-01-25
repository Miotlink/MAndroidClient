package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * on 2016/4/20 0020
 *
 * @author zhudongjie .
 */
public class PictureItemDecoration extends RecyclerView.ItemDecoration {

    private ShapeDrawable mDrawable;

    /**
     * constructor
     * @param width width unit px
     */
    public PictureItemDecoration(int width) {
        mDrawable = new ShapeDrawable();
        mDrawable.setIntrinsicWidth(width);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDrawable.getIntrinsicWidth();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, mDrawable.getIntrinsicWidth(), 0);
    }
}
