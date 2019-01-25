package com.homepaas.sls.util.time;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fqian on 17/5/26.
 */

public class DateUtils {

    /**
     * 时间显示规则：
     * 今天00：00：00后的时间的显示：HH:mm
     * 昨天00：00：00后的时间显示：昨天 HH:mm
     * 前天00：00：00后的时间显示：星期X HH:mm
     * 前天之前的时间显示：yyyy-MM-dd HH:mm
     *
     * @param millis 毫秒数
     */
    public static String convertMillisToDate(long millis) {

        SimpleDateFormat sfd = null;
        String time = null;

        String commonSDF = "HH:mm";
        String yearSDF = "yyyy年MM月dd日 HH:mm";

        Date date = new Date(millis);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);

        Date now = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);

        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);


        if (dateCalendar.after(todayCalendar)) {// 判断是不是今天
            sfd = new SimpleDateFormat(commonSDF);
            time = sfd.format(date);
            return time;
        } else {
            todayCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是昨天
                sfd = new SimpleDateFormat(commonSDF);
                time = new StringBuffer().append("昨天").append(" ").append(sfd.format(date)).toString();
                return time;
            }

            todayCalendar.add(Calendar.DATE, -2);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是前天
                int dayOfWeek = dateCalendar.get(Calendar.DAY_OF_WEEK);
                switch (dayOfWeek) {
                    case Calendar.MONDAY:
                        time = "星期一 ";
                        break;
                    case Calendar.TUESDAY:
                        time = "星期二 ";
                        break;
                    case Calendar.WEDNESDAY:
                        time = "星期三 ";
                        break;
                    case Calendar.THURSDAY:
                        time = "星期四 ";
                        break;
                    case Calendar.FRIDAY:
                        time = "星期五 ";
                        break;
                    case Calendar.SATURDAY:
                        time = "星期六 ";
                        break;
                    case Calendar.SUNDAY:
                        time = "星期日 ";
                        break;

                }
                sfd = new SimpleDateFormat(commonSDF);
                time += sfd.format(date);
                return time;
            }
        }

        //如果是前天之前的时间
        sfd = new SimpleDateFormat(yearSDF);
        time = sfd.format(date);

        return time;
    }

    /**
     * 时间显示规则：
     * 今天00：00：00后的时间的显示：今天HH:mm
     * 明天00：00：00后的时间显示：明天HH:mm
     * 其他：MM月dd日 HH:mm
     *
     * @param millis 毫秒数
     */
    public static String convertMillisToDate2(long millis) {

        String commonSDF = "HH:mm";
        String yearSDF = "MM月dd日 HH:mm";

        Date date = new Date(millis);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);

        Date now = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);

        if (dateCalendar.after(todayCalendar)) {// 判断是不是今天
            SimpleDateFormat sfd = new SimpleDateFormat(commonSDF);
            return new StringBuilder().append("今天").append(sfd.format(date)).toString();
        }
        todayCalendar.add(Calendar.DATE, 1);
        if (dateCalendar.after(todayCalendar)) {// 判断是不是明天
            SimpleDateFormat sfd = new SimpleDateFormat(commonSDF);
            return new StringBuilder().append("明天").append(sfd.format(date)).toString();
        }
        SimpleDateFormat sfd = new SimpleDateFormat(yearSDF);
        return sfd.format(date);
    }

    /**
     * 将标准日期格式转换为中文日期格式
     *
     * @param date 日期字符串
     */
    public static String convertMillisToDate(String date) {

        long millionSeconds = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            millionSeconds = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertMillisToDate(millionSeconds);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式字符串转换为转换对应格式的Date对象
     *
     * @param dateStr 日期字符串
     */
    public static Date convertStrToDate(String dateStr) {
        if (TextUtils.isEmpty(dateStr))
            return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的Date对象转换为yyyy-MM-dd格式的字符串
     *
     * @param date Date对象
     */
    public static String convertDateToStr(Date date) {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 将yyyy-MM-dd格式的Date对象转换为yyyy-MM-dd格式的字符串
     *
     * @param date Date对象
     */
    public static String convertDateToStrYear(Date date) {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }
    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }
}
