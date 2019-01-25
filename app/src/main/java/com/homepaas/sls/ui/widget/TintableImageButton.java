package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

import com.homepaas.sls.R;

/**
 * on 2016/5/20 0020
 *
 * @author zhudongjie
 */
public class TintableImageButton extends AppCompatImageButton {


    private ColorStateList mColorStateList;

    public TintableImageButton(Context context) {
        this(context, null);
    }

    public TintableImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintableImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TintableImageButton, defStyle, 0);
        if (a.hasValue(R.styleable.TintableImageButton_tint)) {
            mColorStateList = a.getColorStateList(R.styleable.TintableImageButton_tint);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setImageTintList(mColorStateList);
            } else {
                wrap(getDrawable());
            }
        }
        a.recycle();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            wrap(getDrawable());
        }
    }

    private void wrap(Drawable drawable) {
        Drawable wrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrap, mColorStateList);
        super.setImageDrawable(wrap);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            wrap(drawable);
        }else {
            super.setImageDrawable(drawable);
        }
    }
}
