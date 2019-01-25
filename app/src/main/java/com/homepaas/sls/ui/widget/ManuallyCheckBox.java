package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * on 2016/3/7 0007
 * 由于需要请求网络后再修改状态，所以用手动控制
 *
 * @author zhudongjie .
 */
public class ManuallyCheckBox extends ImageTextButton {

    private boolean mChecked = false;

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    public ManuallyCheckBox(Context context) {
        this(context, null);
    }

    public ManuallyCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ManuallyCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void setChecked(boolean checked) {
        if (checked != mChecked) {
            mChecked = checked;
            refreshDrawableState();
        }
    }

    public boolean isChecked() {
        return mChecked;
    }
    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (mChecked) {
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
            return drawableState;
        }
        return super.onCreateDrawableState(extraSpace);
    }

}
