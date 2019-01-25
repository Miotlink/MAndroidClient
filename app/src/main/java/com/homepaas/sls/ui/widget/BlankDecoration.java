package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homepaas.sls.R;

/**
 * Created by Sherily on 2016/11/30.
 */

public class BlankDecoration extends RecyclerView.ItemDecoration {
    private Context context;
    private Drawable dividerDrawable;

    public BlankDecoration(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT>=21)
            dividerDrawable = context.getResources().getDrawable(R.drawable.blank_decoration_layout,null);
        else
        {
            dividerDrawable = context.getResources().getDrawable(R.drawable.blank_decoration_layout);
        }
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (childCount - 1 == i)
            {
                super.onDraw(c,parent,state);
                break;
            }
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + dividerDrawable.getIntrinsicHeight();
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0,0,0,dividerDrawable.getIntrinsicHeight());
    }
}
