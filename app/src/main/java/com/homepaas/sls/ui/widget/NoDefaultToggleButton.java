package com.homepaas.sls.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ToggleButton;

/**
 * Created by Administrator on 2016/7/6.
 */
public class NoDefaultToggleButton extends ToggleButton {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NoDefaultToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NoDefaultToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NoDefaultToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void toggle() {
        //do nothing to prevent default action
//        super.toggle();
    }
}
