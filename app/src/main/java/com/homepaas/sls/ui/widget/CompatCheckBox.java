package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CompoundButton;

import com.homepaas.sls.R;

import java.lang.reflect.Field;

/**
 *
 * 一个Image和Text居中的CheckBox
 *
 * @author zhudongjie .
 */
public class CompatCheckBox extends AppCompatCheckBox {

    private static final String TAG = "CompatCheckBox";

    private int mButtonPadding;

    public CompatCheckBox(Context context) {
        this(context, null);
    }

    public CompatCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompatCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CompatCheckBox, defStyleAttr, 0);
        mButtonPadding = typedArray.getDimensionPixelSize(R.styleable.CompatCheckBox_buttonPadding, 0);
        typedArray.recycle();
        getPaint().setColor(getTextColors().getDefaultColor());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        float textWidth = getPaint().measureText(getText().toString());
        int textBaseY = (int) ((getHeight() - getPaint().ascent() - getPaint().descent()) / 2);
        int textBaseX;
        final Drawable buttonDrawable = getButtonDrawable();
        if (buttonDrawable != null) {
            final int drawableHeight = buttonDrawable.getIntrinsicHeight();
            final int drawableWidth = buttonDrawable.getIntrinsicWidth();

            final int top;
            top = (getHeight() - drawableHeight) / 2;
            final int bottom = top + drawableHeight;
            final int left = (int) ((getWidth() - (drawableWidth + textWidth + mButtonPadding)) / 2);
            final int right = left + drawableWidth;
            buttonDrawable.setBounds(left, top, right, bottom);
            buttonDrawable.draw(canvas);
            textBaseX = right + mButtonPadding;
        } else {
            textBaseX = (int) ((getWidth() - textWidth) / 2);
        }

        canvas.drawText(getText().toString(), textBaseX, textBaseY, getPaint());
    }


    @Nullable
    @Override
    public Drawable getButtonDrawable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return super.getButtonDrawable();
        } else {
            try {
                Field field = CompoundButton.class.getDeclaredField("mButtonDrawable");
                field.setAccessible(true);
                return (Drawable) field.get(this);
            } catch (NoSuchFieldException e) {
                Log.e(TAG, "getButtonDrawable: NoSuchFieldException", e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "getButtonDrawable: IllegalAccessException", e);
            }
            return null;
        }
    }

}
