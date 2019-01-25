package com.homepaas.sls.util.time;

import android.os.Message;

public class TimeTaskHelp extends android.os.Handler {

    enum TimeFormatType {
        TIME_TYPE_HMS, //时间格式 00:00:00
        TIME_TYPE_MS, //时间格式 00:00
        TIME_TYPE_S   //时间格式 00
    }

    enum TimeRunType {
        TIME_RUN_COUNT_DOWN, //倒计时
        TIME_RUN_HAVE_END, //计时有时间节点
        TIME_RUN_NO_END  //计时无时间节点
    }

    private TimeFormatType timeType = TimeFormatType.TIME_TYPE_HMS;
    private long plusTime;//提车剩余时间
    private long totalTime;//计时总时长
    private long interval = 1000;//间隔
    private OnTimeChangeListener onTimeChangeListener;
    private static final int WHAT = 0x1001;
    private String currentFrmatTime;//当前格式值

    private TimeRunType timeRunType = TimeRunType.TIME_RUN_COUNT_DOWN;//计时时间格式

    //是否在计时
    private boolean isRun = false;

    /**
     * 开始倒计时
     *
     * @param plusTime
     */
    public void startCountDownTime(long plusTime) {
        startCountDownTime(plusTime, TimeFormatType.TIME_TYPE_MS);
    }

    /**
     * 开始倒计时
     *
     * @param plusTime
     */
    public void startCountDownTimeHMS(long plusTime) {
        startCountDownTime(plusTime, TimeFormatType.TIME_TYPE_HMS);
    }

    /**
     * 开始倒计时
     *
     * @param plusTime
     * @param timeType
     */
    public void startCountDownTime(long plusTime, TimeFormatType timeType) {
        stopTime();
        isRun = true;
        this.plusTime = plusTime * 1000;
        this.interval = 1000;
        this.timeType = timeType;
        this.timeRunType = TimeRunType.TIME_RUN_COUNT_DOWN;
        sendEmptyMessage(WHAT);
    }

    /**
     * 开始计时
     *
     * @param startTime
     */
    public void startTimeNoEnd(long startTime) {
        startTimeNoEnd(startTime, 1, TimeFormatType.TIME_TYPE_HMS);
    }

    /**
     * @param startTime
     * @param interval
     */
    public void startTimeNoEnd(long startTime, long interval) {
        startTimeNoEnd(startTime, interval, TimeFormatType.TIME_TYPE_HMS);
    }

    /**
     * 开始计时
     *
     * @param startTime
     * @param interval
     * @param timeType
     */
    public void startTimeNoEnd(long startTime, long interval, TimeFormatType timeType) {
        stopTime();
        isRun = true;
        this.plusTime = startTime * 1000;
        this.interval = interval * 1000;
        this.timeType = timeType;
        this.timeRunType = TimeRunType.TIME_RUN_NO_END;
        sendEmptyMessage(WHAT);
    }

    /**
     * 开始计时
     *
     * @param totalTime
     */
    public void startTimeHaveEnd(long totalTime) {
        startTimeHaveEnd(totalTime, TimeFormatType.TIME_TYPE_HMS);
    }

    /**
     * 开始计时
     *
     * @param totalTime
     * @param timeType
     */
    public void startTimeHaveEnd(long totalTime, TimeFormatType timeType) {
        stopTime();
        isRun = true;
        this.plusTime = 0;
        this.totalTime = totalTime * 1000;
        this.interval = 1000;
        this.timeType = timeType;
        this.timeRunType = TimeRunType.TIME_RUN_HAVE_END;
        sendEmptyMessage(WHAT);
    }

    //释放资源
    public void stopTime() {
        removeMessages(WHAT);
        isRun = false;
    }

    @Override
    public void handleMessage(Message msg) {
        String formatTime = null;
        switch (timeType) {
            case TIME_TYPE_HMS:
                formatTime = TimeUtils.secToTime((int)plusTime / 1000);
                break;

            case TIME_TYPE_MS:
                formatTime = TimeFormatUtils.getMinuteSecond(plusTime);
                break;

            default:
                formatTime = TimeFormatUtils.getSecond(plusTime);
                break;
        }
        switch (timeRunType) {
            case TIME_RUN_COUNT_DOWN:
                if (plusTime < 1) {
                    changeTimeEnd(formatTime);
                    return;
                }
                break;

            case TIME_RUN_HAVE_END:
                if (plusTime >= totalTime) {
                    changeTimeEnd(formatTime);
                    return;
                }
                break;

            default:
                break;
        }
        changeTime(formatTime);

        switch (timeRunType) {
            case TIME_RUN_COUNT_DOWN:
                plusTime -= this.interval;
                break;

            default:
                plusTime += this.interval;
                break;
        }
    }

    /**
     * 计时过程中
     *
     * @param formatTime
     */
    public void changeTime(String formatTime) {
        if (onTimeChangeListener != null) {
            currentFrmatTime=formatTime;
            onTimeChangeListener.onTimeChange(formatTime);
            sendEmptyMessageDelayed(WHAT, interval);
        }
    }

    /**
     * 计时结束
     *
     * @param formatTime
     */
    public void changeTimeEnd(String formatTime) {
        if (onTimeChangeListener != null) {
            isRun = false;
            currentFrmatTime=formatTime;
            onTimeChangeListener.onTimeChange(formatTime);
            onTimeChangeListener.onTimeCompletion();//计时完成回调
        }
    }

    public String getCurrentFrmatTime() {
        return currentFrmatTime;
    }

    public boolean isRun() {
        return isRun;
    }

    public interface OnTimeChangeListener {
        /**
         * 时间改变
         */
        void onTimeChange(String time);

        /**
         * 计时完成
         */
        void onTimeCompletion();
    }

    public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener) {
        this.onTimeChangeListener = onTimeChangeListener;
    }
}
