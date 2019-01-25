package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;

import com.homepaas.sls.R;

/**
 * Created by CJJ 2016/8/29
 */
public class BreathingView extends View {
    private static final String TAG = "BreathingView";
    private int innerRadius;
    private int gapRadius;
    private int outerRadius;
    private int color;
    private Paint paint;
    private float progress = 0.0f;

    private float curInnerR;
    private float curOuterR;
    private AccelerateDecelerator intepretor;
    private Paint blurPaint;
    private BlurMaskFilter maskFilter;
    private int blurRadius;

    public BreathingView(Context context) {
        super(context);
    }

    public BreathingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BreathingView);
        innerRadius = (int) array.getDimension(R.styleable.BreathingView_innerRadius, 10);
        outerRadius = (int) array.getDimension(R.styleable.BreathingView_outerRadius, 20);
        gapRadius = (int) array.getDimension(R.styleable.BreathingView_gapRadius, 15);
        color = array.getColor(R.styleable.BreathingView_innerColor, Color.CYAN);
        init();
    }

    /**
     * 初始化状态
     */
    private void init() {
        curOuterR = outerRadius;
        curInnerR = gapRadius;
        intepretor = new AccelerateDecelerator();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int height =0;
//        int width = 0;
//
//        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, "onMeasure: " + outerRadius);
        int size = (int) (outerRadius * 2 + (outerRadius - gapRadius) * 2 + gapRadius * 0.1f + getBlurRadius());
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        Paint paint = getCanvasPaint();
        caculateRadius();
        //绘制外层的边缘模糊
        canvas.drawCircle(getWidth()/2,getHeight()/2,curOuterR,getBlurPaint());
        paint.setColor(Color.WHITE);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, curOuterR, paint);
        paint.setColor(color);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, curInnerR, paint);
        postInvalidateDelayed(200);
    }

    private void caculateRadius() {
        progress += 0.0150f;
        float p = intepretor.getInterpolation(progress);
//        float p2 = intepretor.getInterpolation(progress+0.5f);
        curInnerR = innerRadius + p * (gapRadius - innerRadius) / 2;
        curOuterR = outerRadius - (outerRadius-gapRadius)/2+(outerRadius-gapRadius)/2*p;
    }


    public Paint getCanvasPaint() {
        if (paint == null) {
            paint = new Paint();
            paint.setColor(color);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
        }
        return paint;
    }

    public Paint getBlurPaint() {
        if (blurPaint == null) {
            blurPaint = new Paint();
            blurPaint.setColor(Color.GRAY);
            blurPaint.setAntiAlias(true);
            blurPaint.setStyle(Paint.Style.FILL);
            float radius = curOuterR + 1.0f;
            maskFilter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.OUTER);
            blurPaint.setMaskFilter(maskFilter);
        } else {
            blurPaint.setMaskFilter(new BlurMaskFilter(curOuterR / 2 * 0.5f, BlurMaskFilter.Blur.OUTER));
        }
        return blurPaint;
    }

    public int getBlurRadius() {
        if (blurRadius == 0)
        {
            blurRadius = (int) (outerRadius/2*0.5f);
        }
        return blurRadius;
    }

    private static class AccelerateDecelerator implements Interpolator {

        @Override
        public float getInterpolation(float input) {
            return (float) (Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
        }
    }
}
