package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.homepaas.sls.R;


/**
 * Created by CJJ on 2016/FontLimit/25.
 */
public class LoadingView extends View {
    private static final String TAG = "LoadingView";
    private final int DEFAULT_BALLON_NUM = 3;
    private float textSize;
    private Bitmap arrow;
    private float defaultPadding;
    private float ballonPadding;
    private int ballonNum;
    private int color;
    private int MAX_PROGRESS = 100;
    int progress = (int) (MAX_PROGRESS * 0.3);//给一定的初始半径，避免在第一次出现时只有中间的一个气泡显示
    private int minRadius;
    private int maxRadius;
    private int stepSize;
    private float pivotOneX;
    private float pivotSecX;
    private float pivotThirdX;
    private float pivotY;
    private Paint paint;
    private Paint linePaint;
    private int doQueueLen = 30;
    private static final int FontLimit = 10;


    public static final int MODE_LOADING = 0xfff01;
    public static final int MODE_TEXT = 0xfff02;
    private static final int MODE_CHANGING = 0xff03;//正在变化中

    public int mode;
    private Paint textPaint;
    private String text;
    private Paint fillPaint;
    private int width;
    private int height;
    private int oldW;
    private boolean decrease = false;
    private String shortText;
    private float textLen;
    private boolean textOverflow;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadingStyle);
        ballonNum = array.getInt(R.styleable.LoadingStyle_ballon, DEFAULT_BALLON_NUM);
        ballonPadding = array.getDimension(R.styleable.LoadingStyle_ballonPadding, getDefaultPadding());
        color = array.getColor(R.styleable.LoadingStyle_ballonColor, Color.GRAY);
        minRadius = (int) array.getDimension(R.styleable.LoadingStyle_minRadius, 100);
        maxRadius = (int) array.getDimension(R.styleable.LoadingStyle_maxRadius, 300);
        textSize = array.getDimension(R.styleable.LoadingStyle_textSize, 0);
        text = array.getString(R.styleable.LoadingStyle_text);
        stepSize = (maxRadius - minRadius) / 100;
        init();
        Log.i(TAG, "Category: maxRadius:" + maxRadius + "\n" +
                "ballonPadding:" + ballonPadding + "\n " +
                "minRadius:" + minRadius + "\n " +
                "ballonNum:" + ballonNum + "\n" +
                "textSize:" + textSize);
    }

    public void init() {
        arrow = BitmapFactory.decodeResource(getResources(), R.mipmap.place);
        mode = MODE_LOADING;
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int computedExtWidth;
        Log.i(TAG, "onMeasure: sizeWidth:sizeHeight  " + sizeWidth + ":" + sizeHeight);
        if (mode == MODE_LOADING)
            computedExtWidth = (int) (getDefaultPadding() * 2 + (maxRadius + ballonPadding) * 2 * ballonNum) + getPaddingLeft() + getPaddingRight();
        else {
            computedExtWidth = (int) (getTextPaint().measureText(textOverflow ? text.substring(0, FontLimit)+"..." : text) + getPaddingLeft() + getPaddingRight() + ballonPadding * 2 + getDefaultPadding() * 2);
        }
        int computeHeight = (int) (getDefaultPadding() * 2 + (maxRadius + ballonPadding) * 2);
        setMeasuredDimension(computedExtWidth, computeHeight);
        computePosForBallon();
        //在measure阶段确定最新的宽度
    }

    private void computePosForBallon() {
        pivotY = (float) ((getMeasuredHeight() - arrow.getHeight()) / 2 + 0.0);
        pivotOneX = (ballonPadding + maxRadius) + getDefaultPadding();
        pivotSecX = (pivotOneX + (maxRadius + ballonPadding) * 2);
        pivotThirdX = (pivotSecX + (maxRadius + ballonPadding) * 2);
    }

    public static Path RoundedRect(float left, float top, float right, float bottom, float rx, float ry,
                                   boolean tl, boolean tr, boolean br, boolean bl) {
        Path path = new Path();
        if (rx < 0) {
            rx = 0;
        }
        if (ry < 0) {
            ry = 0;
        }
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) {
            rx = width / 2;
        }
        if (ry > height / 2) {
            ry = height / 2;
        }
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        if (tr)
            path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        else{
            path.rLineTo(0, -ry);
            path.rLineTo(-rx,0);
        }
        path.rLineTo(-widthMinusCorners, 0);
        if (tl)
            path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        else{
            path.rLineTo(-rx, 0);
            path.rLineTo(0,ry);
        }
        path.rLineTo(0, heightMinusCorners);

        if (bl)
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
        else{
            path.rLineTo(0, ry);
            path.rLineTo(rx,0);
        }

        path.rLineTo(widthMinusCorners, 0);
        if (br)
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        else{
            path.rLineTo(rx,0);
            path.rLineTo(0, -ry);
        }

        path.rLineTo(0, -heightMinusCorners);
        path.close();//Given close, last lineto can be removed.

        return path;
    }

    /**
     * 初始气泡为小 中 大
     **/
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = (maxRadius * progress / MAX_PROGRESS);
        int radiusMiddle = maxRadius - radius;
//        float radiusOne = (float) ((0.85 * maxRadius * progress / MAX_PROGRESS + minRadius) % maxRadius);
//        float radiusSec = (float) ((1.08 * maxRadius * progress / MAX_PROGRESS + minRadius) % maxRadius);
//        float radiusThird = (float) (1.2 * maxRadius * progress / MAX_PROGRESS + minRadius) % maxRadius;
//        canvas.drawRoundRect(1, 1, getMeasuredWidth() - 2, getMeasuredHeight() - arrow.getHeight() - 2, 5, 5, getFillPaint());
//        canvas.drawRoundRect(0, 0, getMeasuredWidth() - 2, getMeasuredHeight() - arrow.getHeight() - 2, 5, 5, getLinePaint());
        Path path1 = RoundedRect(1, 1, getMeasuredWidth() - 2, getMeasuredHeight() - arrow.getHeight() - 2, 5, 5, true, true, true, true);
        canvas.drawPath(path1, getFillPaint());
        Path path2 = RoundedRect(0, 0, getMeasuredWidth() - 2, getMeasuredHeight() - arrow.getHeight() - 2, 5, 5, true, true, true, true);
        canvas.drawPath(path2, getLinePaint());
        canvas.drawBitmap(arrow, (getMeasuredWidth() - arrow.getWidth()) / 2, getMeasuredHeight() - arrow.getHeight() - 4, null);
        if (mode == MODE_LOADING) {
//            Log.i(TAG, "onDraw: circle:[" + pivotOneX + "," + pivotY + "], [" + pivotSecX + "," + pivotY + "] ,[" + pivotThirdX + "," + pivotY + "]");
            canvas.drawCircle(pivotOneX, pivotY, radius, getSelfPaint());
            canvas.drawCircle(pivotSecX, pivotY, radiusMiddle, getSelfPaint());
            canvas.drawCircle(pivotThirdX, pivotY, radius, getSelfPaint());
            updateProgress();
            postInvalidateDelayed(100);
        } else if (mode == MODE_TEXT) {
            //文字
            Paint textPaint = getTextPaint();
            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
            Rect bounds = new Rect();
            getDrawingRect(bounds);
            bounds.bottom -= arrow.getHeight();
            // code below refer to http://blog.csdn.net/hursing
            int baseline = (bounds.bottom + bounds.top - fontMetrics.bottom - fontMetrics.top) / 2;
            // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
            getSelfPaint().setTextAlign(Paint.Align.LEFT);
            canvas.drawText(textOverflow?text.substring(0,FontLimit)+"...":text, getDefaultPadding() + ballonPadding + getMeasuredWidth() / 2 - bounds.width() / 2, baseline, textPaint);
        } else {
            //变化之中，绘制一片空白

        }
    }

    private void updateProgress() {
        if (decrease)
            progress -= MAX_PROGRESS * 0.1;
        else
            progress += MAX_PROGRESS * 0.1;
        if (progress > MAX_PROGRESS)//减速
        {
            decrease = true;
            progress = (int) (0.9 * MAX_PROGRESS);
        } else if (progress < minRadius) {//加速
            decrease = false;
        }
    }

    public float getDefaultPadding() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        defaultPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, displayMetrics);
        return defaultPadding;
    }

    public Paint getSelfPaint() {
        if (paint == null) {
            paint = new Paint();
            paint.setColor(color);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(3);
        }
        return paint;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        arrow.recycle();
    }

    public Paint getLinePaint() {
        if (linePaint == null) {
            linePaint = new Paint();
            linePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics()));
            linePaint.setAntiAlias(true);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setColor(color);
        }
        return linePaint;
    }

    public Paint getTextPaint() {
        if (textPaint == null) {
            textPaint = new Paint();
            textPaint.setTextSize(textSize);
            textPaint.setColor(Color.BLACK);
            textPaint.setAntiAlias(true);
            textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            textPaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
            textPaint.setTextAlign(Paint.Align.LEFT);
//            int baseline = get.top + (targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        }
        return textPaint;
    }

    public void setText(String text) {
//        this.mode = MODE_TEXT;
        if (text==null)
            return;
        this.text = text;
        computeAndTriggerLayoutAnimation();
    }

    /**
     * 触发一个ScaleAnimation ，在动画结束时invalidate更新布局
     */
    private void computeAndTriggerLayoutAnimation() {
        if (text.length() > FontLimit)
            textOverflow = true;
        else
            textOverflow = false;
        if (textOverflow)
            textLen = getSelfPaint().measureText(text.substring(0, FontLimit));
       /* int newW = (int) (textLen + getPaddingLeft() + getPaddingRight() + ballonPadding * 2 + getDefaultPadding() * 2);
        width = newW;*/
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mode = MODE_TEXT;
                requestLayout();
                invalidate();
            }
        }, 100);
    }

    public void setMinRadius(int minRadius) {
        this.minRadius = dp2px(minRadius);
    }

    public void setMaxRadius(int maxRadius) {
        this.maxRadius = dp2px(maxRadius);
    }

    public void setBallonPadding(float ballonPadding) {
        this.ballonPadding = dp2px((int) ballonPadding);
    }

    public void setBallonNum(int ballonNum) {
        this.ballonNum = ballonNum;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public Paint getFillPaint() {
        if (fillPaint == null) {
            fillPaint = new Paint();
            fillPaint.setColor(Color.WHITE);
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setAntiAlias(true);
        }
        return fillPaint;
    }
}
