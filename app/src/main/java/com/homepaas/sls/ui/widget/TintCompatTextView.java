package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.homepaas.sls.R;

/**
 * on 2016/7/12 0012
 *
 * @author zhudongjie
 */
public class TintCompatTextView extends AppCompatTextView {

    private ColorStateList mColorStateList;


    public TintCompatTextView(Context context) {
        this(context, null);
    }

    public TintCompatTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintCompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TintCompatTextView, defStyleAttr, 0);
        if (a.hasValue(R.styleable.TintCompatTextView_drawableTint)) {
            mColorStateList = a.getColorStateList(R.styleable.TintCompatTextView_drawableTint);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setCompoundDrawableTintList(mColorStateList);
            } else {
                wrap();
            }
        }
        a.recycle();
    }

    private void wrap() {
        Drawable[] compoundDrawables = getCompoundDrawables();
        Drawable[] wrappedDrawables = new Drawable[4];
        for (int i = 0; i < compoundDrawables.length; i++) {
            Drawable dr = compoundDrawables[i];
            if (dr != null) {
                Drawable wrap = DrawableCompat.wrap(dr);
                DrawableCompat.setTintList(wrap, mColorStateList);
                wrappedDrawables[i] = wrap;
            }
        }
        super.setCompoundDrawables(wrappedDrawables[0], wrappedDrawables[1], wrappedDrawables[2], wrappedDrawables[3]);
    }


    public void setDrawableLeft(@DrawableRes int resId){
        setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            wrap();
        }
    }
}
