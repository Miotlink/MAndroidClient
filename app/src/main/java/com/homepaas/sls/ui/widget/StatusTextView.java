package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.homepaas.sls.R;

/**
 * Created by CJJ on 2016/9/14.
 */

public class StatusTextView extends TextView implements View.OnClickListener {

    public boolean isChecked;
    private final int[] status = new int[]{R.attr.sls_checked};

    public StatusTextView(Context context) {
        super(context);
    }

    public StatusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] ints = super.onCreateDrawableState(extraSpace+1);
        if (isChecked)
        {
            mergeDrawableStates(ints,status);
        }
        return ints;
    }

    @Override
    public void onClick(View v) {
        isChecked=!isChecked;
        if (isChecked)
        {
            refreshDrawableState();
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
    }
}
