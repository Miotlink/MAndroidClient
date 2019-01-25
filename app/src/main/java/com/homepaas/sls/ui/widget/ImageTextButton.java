package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.widget.TintableCompoundButton;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.homepaas.sls.R;

/**
 * 图片和文字居中的按钮
 * @author zhudongjie
 */
public class ImageTextButton extends View {

    private static final String TAG = "ImageTextButton";

    private static final boolean DEBUG = false;

    @IntDef({LEFT, RIGHT, TOP, BOTTOM})
    @interface Direction {}

    private Drawable mDrawable;

    private String mText = "";

    private TextPaint mPaint;

    private ColorStateList mColorStateList;

    private int mCurrentColor = Color.BLACK;

    private int mDrawablePadding;

    private static final int DEFAULT_SIZE = 15;

    private int mDrawableDirection = LEFT;

    public static final int LEFT = 0;

    public static final int RIGHT = 1;

    public static final int TOP = 2;

    public static final int BOTTOM = 3;

    public ImageTextButton(Context context) {
        this(context, null);
    }

    public ImageTextButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageTextButton, defStyle, 0);

        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.density = context.getResources().getDisplayMetrics().density;
        if (a.hasValue(R.styleable.ImageTextButton_android_text)) {
            mText = a.getString(R.styleable.ImageTextButton_android_text);
        }
        float defaultSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_SIZE, context.getResources().getDisplayMetrics());
        int size = a.getDimensionPixelSize(R.styleable.ImageTextButton_android_textSize, (int) defaultSize);
        mPaint.setTextSize(size);
        if (a.hasValue(R.styleable.ImageTextButton_android_textColor)) {
            mColorStateList = a.getColorStateList(R.styleable.ImageTextButton_android_textColor);
            if (mColorStateList != null)
                mCurrentColor = mColorStateList.getDefaultColor();
        }

        if (a.hasValue(R.styleable.ImageTextButton_android_src)) {
            mDrawable = a.getDrawable(R.styleable.ImageTextButton_android_src);
        }

        mDrawableDirection = a.getInt(R.styleable.ImageTextButton_drawableDirection, LEFT);
        mDrawablePadding = a.getDimensionPixelSize(R.styleable.ImageTextButton_android_drawablePadding, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        float textWidth = mPaint.measureText(mText);
        float textHeight = mPaint.descent() - mPaint.ascent() + mPaint.getFontMetrics().leading;
        int drawableWidth = 0;
        int drawableHeight = 0;
        if (mDrawable != null) {
            drawableHeight = mDrawable.getIntrinsicHeight();
            drawableWidth = mDrawable.getIntrinsicWidth();
        }
        boolean isVertical = mDrawableDirection == TOP || mDrawableDirection == BOTTOM;
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {

            if (isVertical) {
                width = (int) Math.max(drawableWidth, textWidth);
            } else {
                width = (int) (drawableWidth + mDrawablePadding + textWidth);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            if (isVertical) {
                height = (int) (drawableHeight + mDrawablePadding + textHeight);
            } else {
                height = (int) Math.max(drawableHeight, textHeight);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float textWidth = mPaint.measureText(mText);
        float textHeight = mPaint.descent() - mPaint.ascent() + mPaint.getFontMetrics().leading;
        int drawableWidth = 0;
        int drawableHeight = 0;
        if (mDrawable != null) {
            drawableHeight = mDrawable.getIntrinsicHeight();
            drawableWidth = mDrawable.getIntrinsicWidth();
        }
        //text base line,
        float baseX;
        float baseYTemp;//左上Y值
        //drawable position
        int left;
        int top;
        boolean isVertical = mDrawableDirection == TOP || mDrawableDirection == BOTTOM;
        if (isVertical) {
            baseX = (int) (getWidth() / 2 - textWidth / 2);
            left = getWidth() / 2 - drawableWidth / 2;
            if (mDrawableDirection == TOP) {
                top = (int) ((getHeight() - (drawableHeight + mDrawablePadding + textHeight)) / 2);
                baseYTemp = top + drawableHeight + mDrawablePadding;
            } else {
                baseYTemp = (int) ((getHeight() - (drawableHeight + mDrawablePadding + textHeight)) / 2);
                top = (int) (baseYTemp + mDrawablePadding + textHeight);
            }
        } else {
            baseYTemp = (int) (getHeight() / 2 - textHeight / 2);
            top = (getHeight() - drawableHeight) / 2;
            if (mDrawableDirection == LEFT) {
                left = (int) ((getWidth() - (drawableWidth + mDrawablePadding + textWidth)) / 2);
                baseX = left + drawableWidth + mDrawablePadding;
            } else {
                baseX = (getWidth() - (drawableWidth + mDrawablePadding + textWidth)) / 2;
                left = (int) (baseX + textWidth + mDrawablePadding);
            }
        }

        if (mDrawable != null) {
            final int right = left + drawableWidth;
            final int bottom = top + drawableHeight;
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }
        mPaint.setColor(mCurrentColor);
        // FIXME: 2016/3/7 0007 baseY的计算好像有点问题
        float baseY = baseYTemp - (mPaint.ascent() + mPaint.getFontMetrics().leading);
        canvas.drawText(mText, baseX, baseY, mPaint);
    }


    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
        invalidate();
        requestLayout();
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
        invalidate();
        requestLayout();
    }

    public int getDrawablePadding() {
        return mDrawablePadding;
    }

    public void setDrawablePadding(int drawablePadding) {
        mDrawablePadding = drawablePadding;
        invalidate();
        requestLayout();
    }

    public int getDrawableDirection() {
        return mDrawableDirection;
    }

    public void setDrawableDirection(@Direction int drawableDirection) {
        mDrawableDirection = drawableDirection;
        invalidate();
        requestLayout();
    }

    public ColorStateList getColorStateList() {
        return mColorStateList;
    }

    public void setColorStateList(ColorStateList colorStateList) {
        mColorStateList = colorStateList;
        updateTextColors();
    }

    public int getCurrentColor() {
        return mCurrentColor;
    }

    public void setColor(int color) {
        mColorStateList = ColorStateList.valueOf(color);
        updateTextColors();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mColorStateList != null && mColorStateList.isStateful()) {
            updateTextColors();
        }
        if (mDrawable != null && mDrawable.isStateful()) {
            final int[] state = getDrawableState();
            mDrawable.setState(state);
        }
    }

    private void updateTextColors() {
        boolean inval = false;
        int color = mColorStateList.getColorForState(getDrawableState(), 0);
        if (color != mCurrentColor) {
            mCurrentColor = color;
            inval = true;
        }
        if (inval) {
            invalidate();
        }
    }

}

