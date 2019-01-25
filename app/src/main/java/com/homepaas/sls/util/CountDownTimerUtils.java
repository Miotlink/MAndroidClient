package com.homepaas.sls.util;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sherily on 2017/3/25.
 */

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;
    private boolean isCustomStyle = false;
    private int mTextColor;
    private float mTextSize;
    private String mTextContent;
    private long curMillisUntilFinished;
    private Timer mTimer;

    private TimerTask mTask;
    private long millisInFuture;
    private long countDownInterval;
    private long leftTime;
    private int COUNT_TIME = 0;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtils(long millisInFuture, long countDownInterval, TextView mTextView) {
        super(millisInFuture, countDownInterval);
        this.mTextView = mTextView;
//        this.millisInFuture = millisInFuture;
//        this.countDownInterval = countDownInterval;
//        leftTime = 0;
//        mTimer = new Timer();

    }
//
//    private Handler mHandler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            millisInFuture -= countDownInterval;
//            if (millisInFuture <= 0){
//                onTick(0);
//                onFinish();
//                if (mTask != null){
//                    mTask.cancel();
//                }
//
//            } else {
//                onTick(millisInFuture);
//            }
//            return true;
//        }
//    });
//
//    public void cancel(){
//        //释放资源
//        if(mTask != null){
//            mTask.cancel();
//            mTask = null;
//        }
//        if(mTimer != null){
//            mTimer.cancel();
//            mTimer = null;
//        }
//        if (mHandler != null){
//            mHandler.removeCallbacksAndMessages(null);
//        }
//    }
//    public void start() {
//        if (millisInFuture < countDownInterval) {
//            cancel();
//            onTick(0);
//            onFinish();
//            return;
//        }
//        onTick(millisInFuture);
//        mTask = new TimerTask() {
//            @Override
//            public void run() {
//                mHandler.sendEmptyMessage(COUNT_TIME);
//            }
//        };
//        mTimer.schedule(mTask, 0, countDownInterval);
//    }
    public void setStyle(int color,float textSize,String text){
        mTextColor = color;
        mTextSize = textSize;
        mTextContent = text;
        isCustomStyle = true;
    }
    public SpannableStringBuilder matcherSearchText(int color, float textSize, String text, long time) {
        if ( textSize == 0.0f )
            textSize = 15f;
        String regx = "\\d+:\\d+";
        String content = Pattern.compile(regx).matcher(text).replaceAll(formatTime(time));
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, mTextView.getContext().getResources().getDisplayMetrics());
        SpannableStringBuilder ss = new SpannableStringBuilder(content);
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(size), start , end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        }
        return ss;
    }

    public interface OnFinishListner {
        void onFinish(boolean isFinish);
    }
    private OnFinishListner onFinishListner;

    public void setOnFinishListner(OnFinishListner onFinishListner) {
        this.onFinishListner = onFinishListner;
    }

    public String timeParse(long duration) {
        String time = "" ;

        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;

        long second = Math.round((float)seconds/1000) ;

        if( minute < 10 ){
            time += "0" ;
        }
        time += minute+":" ;

        if( second < 10 ){
            time += "0" ;
        }
        time += second ;

        return time ;
    }
    private String formatTime(long time){
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(time);
    }
    @Override
    public void onTick(long millisUntilFinished) {
        curMillisUntilFinished = millisUntilFinished;

        if (isCustomStyle){
            mTextView.setText(matcherSearchText(mTextColor,mTextSize,mTextContent,millisUntilFinished));
        } else {
            mTextView.setText(formatTime(millisUntilFinished));  //设置倒计时时间
        }
    }

    public long getCurMillisUntilFinished() {
        return curMillisUntilFinished;
    }

    @Override
    public void onFinish() {
        if (onFinishListner != null){
            onFinishListner.onFinish(true);
        }
    }
}
