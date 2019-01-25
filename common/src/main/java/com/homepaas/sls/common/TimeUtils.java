package com.homepaas.sls.common;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * on 2016/2/18 0018
 *
 * @author zhudongjie .
 */
public class TimeUtils {

    private static final int HOUR = 60 * 60;

    private static final int MINUTE = 60;

    private static String[] weekDays = "周日 周一 周二 周三 周四 周五 周六".split(" ");

    private static final long ONE_DAY = 24 * 60 * 60 * 1000L;

    private TimeUtils() {
    }

    /**
     * 格式化时间
     *
     * @param timestamp 秒时
     * @return 格式化后的时间
     */
    public static String formatDate(String timestamp) {
        if (TextUtils.isEmpty(timestamp)) {
            return "";
        } else {
            long time = Long.parseLong(timestamp) * 1000;
            Date date = new Date(time);
            String format;

            if (time >= today() && time < today() + ONE_DAY) {
                format = "";
            } else if (time >= today() + ONE_DAY && time < today() + ONE_DAY * 2) {
                format = "明天 ";
            } else if (time >= today() + ONE_DAY * 2 && time < today() + ONE_DAY * 3) {
                format = "后天 ";
            } else if (time >= today() - ONE_DAY && time < today()) {
                format = "昨天 ";
            } else if (time >= today() - ONE_DAY * 2 && time < today() - ONE_DAY) {
                format = "前天 ";
            } else if (time >= thisWeek() && time < thisWeek() + ONE_DAY * 7) {
                format = weekDays[dayOfWeek(time) - 1] + " ";
            } else {
                format = "yyyy年MM月dd日 ";
            }
            if (afterNoon(time)) {
                format += "下午 ";
            } else {
                format += "上午 ";
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format + " KK:mm", Locale.CHINA);
            return simpleDateFormat.format(date);
        }
    }


    /**
     * 格式化月份
     *
     * @param timestamp timestamp 秒时
     * @return 格式化后的月份
     */
    public static String formatMonth(String timestamp) {
        if (TextUtils.isEmpty(timestamp)) {
            return "";
        } else {
            String format;
            int month = getMonth(timestamp);
            int year = getYear(timestamp);
            if (year == thisYear()) {
                if (month == thisMonth()) {
                    format = "本月";
                } else {
                    format = month + "月";
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(year).append("年").append(month).append("月");
                format = sb.toString();
            }

            return format;
        }
    }

    /**
     * 获取今天凌晨的毫秒时
     *
     * @return 今天凌晨的毫秒时
     */
    public static long today() {
        long current = System.currentTimeMillis();
        return current - current % ONE_DAY;
    }

    /**
     * 获取今天是几号
     *
     * @return
     */
    public static int date() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 一星期中的第几天
     *
     * @param time 毫秒时
     * @return 1-7 ：周日-周六
     */
    public static int dayOfWeek(long time) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断是否为一天中的下午
     *
     * @param time 毫秒时
     * @return true, 如果该时刻是一天中的下午
     */
    public static boolean afterNoon(long time) {
        return (time + TimeZone.getDefault().getRawOffset()) % ONE_DAY > ONE_DAY / 2;
    }


    /**
     * 获取本周日零时的毫秒时
     *
     * @return 本周日零时的毫秒时
     */
    public static long thisWeek() {
        return today() - (dayOfWeek(System.currentTimeMillis()) - 1) * ONE_DAY;
    }

    /**
     * 获取月份
     *
     * @param timestamp 秒时
     * @return 第几个月 JANUARY 对应 1，FEBRUARY 对应 2
     */
    public static int getMonth(String timestamp) {
        long time = Long.parseLong(timestamp) * 1000;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取年份
     *
     * @param timestamp 秒时
     * @return 年份
     */
    public static int getYear(String timestamp) {
        long time = Long.parseLong(timestamp) * 1000;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前年份
     *
     * @return 当前年份
     */
    public static int thisYear() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return 第几个月 JANUARY 对应 1，FEBRUARY 对应 2
     */
    public static int thisMonth() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * 格式化时间 03:12:15
     *
     * @param timeString 秒
     * @return 格式化后的时间
     */
    public static String formatTime(String timeString) {
        if (TextUtils.isEmpty(timeString)) {
            return "";
        } else {
            Long time = Long.parseLong(timeString);
            int hours = (int) (time / HOUR);
            int minute = (int) ((time - hours * HOUR) / MINUTE);
            int second = (int) ((time - hours * HOUR) % MINUTE);
            return String.format(Locale.CHINA, "%02d:%02d:%02d", hours, minute, second);
        }
    }

    public static String formatOrderTime(String timestamp) {
        if (TextUtils.isEmpty(timestamp))
            return "";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(new Date(Long.parseLong(timestamp) * 1000));
    }

    public static String formatCouponTime(String timestamp) {
        if (TextUtils.isEmpty(timestamp))
            return "";
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(Long.parseLong(timestamp) * 1000));
    }

    /**
     * 仅仅针对“2016/1/6 周一”格式
     *
     * @return
     */
    public static String formatSmartTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        } else {
            String cutString = time.substring(0, time.indexOf(" "));
            if (time.contains(" ")) {
                int m = Integer.parseInt(time.substring(time.indexOf("/") + 1, time.lastIndexOf("/")));
                int d = Integer.parseInt(time.substring(time.lastIndexOf("/") + 1, time.indexOf(" ")));
                int thisMoth = thisMonth();
                int date = date();
                if (m == thisMoth) {
                    int differ = -date + d;
                    if (differ == 0)
                        return cutString + " 今天";
                    if (differ == 1)
                        return cutString + " 明天";
                    if (differ == 2)
                        return cutString + " 后天";
                    return time;
                }
            }
            return time;
        }
    }

    public static String formatDateByLine(String timestamp) {
        if (TextUtils.isEmpty(timestamp)) {
            return "";
        } else {
            long time = Long.parseLong(timestamp);
            String pattern = "yyyy-MM-dd  HH:mm";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(time * 1000));
        }
    }

    public static String getCurrentTime() {
        long time = System.currentTimeMillis();
        String pattern = "yyyy-MM-dd  HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }


    /**
     * 根据一个特殊的规则来格式化
     */
    public static String formatNewDate(String timestamp, String dateTemplate) {
        if (TextUtils.isEmpty(timestamp)) {
            return "";
        } else {
            long time = Long.parseLong(timestamp) * 1000;
            Date date = new Date(time);
            String format;
            if (time >= today() && time < today() + ONE_DAY) {
                format = "今天";
            } else if (time >= today() + ONE_DAY && time < today() + ONE_DAY * 2) {
                format = "明天 ";
            } else if (time >= today() + ONE_DAY * 2 && time < today() + ONE_DAY * 3) {
                format = "后天 ";
            } else if (time >= today() - ONE_DAY && time < today()) {
                format = "昨天 ";
            } else {
                format = dateTemplate + " ";
            }
            format += " HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
            return simpleDateFormat.format(date);
        }
    }

    public static String formatLateTime(String timestamp) {
        if (TextUtils.isEmpty(timestamp))
            return "";
        return new SimpleDateFormat("HH:mm", Locale.CHINA).format(new Date(Long.parseLong(timestamp) * 1000));
    }

    /**
     * 获取当前时间
     */
    public static String getYearMonth() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

}
