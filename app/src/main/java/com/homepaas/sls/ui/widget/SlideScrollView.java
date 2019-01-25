package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class SlideScrollView extends ScrollView {

    private View inner;

    private float y;

    private Rect normal = new Rect();

    private boolean isCount = false;

    public SlideScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    protected void onFinishInflate() {

        if (getChildCount() > 0) {

            inner = getChildAt(0);

        }

    }


    public boolean onTouchEvent(MotionEvent ev) {

        if (inner != null) {

            commOnTouchEvent(ev);

        }

        return super.onTouchEvent(ev);
    }


    private void commOnTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {

                    animation();
                    isCount = false;

                }

                break;

            case MotionEvent.ACTION_MOVE:

                final float preY = y;
                float nowY = ev.getY();

                int deltaY = (int) (preY - nowY);
                if (!isCount) {

                    deltaY = 0;

                }

                y = nowY;

                if (isNeedMove()) {

                    if (normal.isEmpty()) {
                        normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());

                    }

                    inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2, inner.getRight(), inner.getBottom() - deltaY / 2);

                }
                isCount = true;
                break;
        }

    }

    private void animation() {


        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(), normal.top);

        ta.setDuration(200);
        inner.startAnimation(ta);


        inner.layout(normal.left, normal.top, normal.right, normal.bottom);

        normal.setEmpty();


    }

    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    private boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();

        int scrollY = getScrollY();

        if (scrollY == 0 || scrollY == offset) {

            return true;

        }
        return false;

    }

}
