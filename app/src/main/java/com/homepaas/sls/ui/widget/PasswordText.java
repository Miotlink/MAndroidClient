package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;

import com.homepaas.sls.R;

/**
 * on 2015/12/29 0029
 *
 * @author zhudongjie .
 */
public class PasswordText extends AppCompatEditText {

    private boolean mClickOnIcon;

    private boolean mShowPassword;

    public PasswordText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        setDrawableRight(R.mipmap.ic_password_invisible);
    }

    public PasswordText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        setDrawableRight(R.mipmap.ic_password_invisible);
    }


    private void setDrawableRight(int drawable){
        setCompoundDrawablesWithIntrinsicBounds(0,0,drawable,0);
    }

    /**
     * 固定为SANS_SERIF字体，和NUmber inputType保持一致
     * @param tf
     */
    @Override
    public void setTypeface(Typeface tf) {
        Typeface t = Typeface.SANS_SERIF;
        super.setTypeface(t);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                getTotalPaddingRight();
                if (event.getX() <= getWidth()-getPaddingRight()&&event.getX()>getWidth()-getCompoundPaddingRight()) {
                    mClickOnIcon = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                // FIXME: 2016/3/4 0004 setError后失效
                if (event.getX() <= getWidth()-getPaddingRight()&&event.getX()>getWidth()-getCompoundPaddingRight()&&mClickOnIcon) {
                    if (mShowPassword) {
                        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                        setDrawableRight(R.mipmap.ic_password_invisible);
                    } else {
                        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        setDrawableRight(R.mipmap.ic_password_visible);
                    }
                    mShowPassword = !mShowPassword;
                    mClickOnIcon = false;
                }
                break;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }
}
