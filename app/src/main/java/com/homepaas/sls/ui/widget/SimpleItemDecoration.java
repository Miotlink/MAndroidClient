package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.util.DensityUtil;

/**
 * on 2016/2/16 0016
 *
 * @author zhudongjie .
 */
public class SimpleItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    public SimpleItemDecoration(Context context, int orientation, int intrinsicHeight,int color) {
        //  final TypedArray a = context.obtainStyledAttributes(ATTRS);
        //  mDivider = a.getDrawable(0);
        //  a.recycle();
        ShapeDrawable drawable = new ShapeDrawable();
        drawable.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.ADD);
        if (orientation == VERTICAL_LIST)
            drawable.setIntrinsicHeight(DensityUtil.dip2px(context, intrinsicHeight));
        else
            drawable.setIntrinsicWidth(DensityUtil.dip2px(context, intrinsicHeight));
        mDivider = drawable;
//        setOrientation(orientation);
        mOrientation = orientation;
    }

    public SimpleItemDecoration(Context context, int orientation, int intrinsicHeight) {
        //  final TypedArray a = context.obtainStyledAttributes(ATTRS);
        //  mDivider = a.getDrawable(0);
        //  a.recycle();
        ShapeDrawable drawable = new ShapeDrawable();
        drawable.setColorFilter(ContextCompat.getColor(context, R.color.colorDivider), PorterDuff.Mode.ADD);
        if (orientation == VERTICAL_LIST)
            drawable.setIntrinsicHeight(DensityUtil.dip2px(context, intrinsicHeight));
        else
            drawable.setIntrinsicWidth(DensityUtil.dip2px(context, intrinsicHeight));
        mDivider = drawable;
//        setOrientation(orientation);
        mOrientation = orientation;
    }


    public SimpleItemDecoration(Context context, int orientation) {
        //  final TypedArray a = context.obtainStyledAttributes(ATTRS);
        //  mDivider = a.getDrawable(0);
        //  a.recycle();
        ShapeDrawable drawable = new ShapeDrawable();
        drawable.setColorFilter(ContextCompat.getColor(context, R.color.colorDivider), PorterDuff.Mode.ADD);
        if (orientation == VERTICAL_LIST)
            drawable.setIntrinsicHeight(DensityUtil.dip2px(context, 0.5f));
        else
            drawable.setIntrinsicWidth(DensityUtil.dip2px(context, 0.5f));
        mDivider = drawable;
//        setOrientation(orientation);
        mOrientation = orientation;
    }


//    public void setOrientation(int orientation) {
//        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
//            throw new IllegalArgumentException("invalid orientation");
//        }
//        mOrientation = orientation;
//    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //一般最后一个不需要分割线
//            if (i == childCount - 1)
//                return;
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //一般最后一个不需要分割线
//            if (i == childCount - 1)
//                return;
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
