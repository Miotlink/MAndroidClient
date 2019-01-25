package com.homepaas.sls.util.time;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormatUtils {

    /**
     * 获取小时分钟秒
     *
     * @param milliseconds
     * @return
     */
    public static String getHourMinuteSecond(long milliseconds) {
        long hour = milliseconds / (60 * 60 * 1000);
        String h = hour == 0 ? "00" : (hour < 10 ? "0" + hour : hour + "");
        long minute = (milliseconds - hour * 60 * 60 * 1000) / (60 * 1000);
        String m = minute == 0 ? "00" : (minute < 10 ? "0" + minute : minute
                + "");
        long second = (milliseconds - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
        String s = second == 0 ? "00" : (second < 10 ? "0" + second : second
                + "");
        return h + ":" + m + ":" + s;
    }

    /**
     * 获取分钟秒
     *
     * @param milliseconds
     * @return
     */
    public static String getMinuteSecond(long milliseconds) {
        long hour = milliseconds / (60 * 60 * 1000);
        long minute = (milliseconds - hour * 60 * 60 * 1000) / (60 * 1000);
        String m = minute == 0 ? "00" : (minute < 10 ? "0" + minute : minute
                + "");
        long second = (milliseconds - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
        String s = second == 0 ? "00" : (second < 10 ? "0" + second : second
                + "");
        return m + ":" + s;
    }

    /**
     * 格式化分钟秒，获取long类型值
     *
     * @param milliseconds
     * @return 返回毫秒值
     */
    public static int getLongMilliseconds(String milliseconds) {
        String[] split = milliseconds.split(":");
        //split[0]分钟值 split[1]//秒值
        int l = Integer.parseInt(split[0]);
        int l1 = Integer.parseInt(split[1]);
        int value = (l * 60) + l1;
        return value;
    }


    /**
     * 验证码获取秒0-60
     *
     * @param milliseconds
     * @return
     */
    public static String getSecond(long milliseconds) {
        long hour = milliseconds / (60 * 60 * 1000);
        long minute = (milliseconds - hour * 60 * 60 * 1000) / (60 * 1000);
        String m = minute == 0 ? "00" : (minute < 10 ? "0" + minute : minute
                + "");
        long second = (milliseconds - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
        String s = second + "";
        return s;
    }


    //计算两个日期相差年数
    public static int yearDateDiff(String startDate, String endDate) {
        Calendar calBegin = Calendar.getInstance(); //获取日历实例
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(stringTodate(startDate, "yyyy")); //字符串按照指定格式转化为日期
        calEnd.setTime(stringTodate(endDate, "yyyy"));
        return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
    }

    //字符串按照指定格式转化为日期
    public static Date stringTodate(String dateStr, String formatStr) {
        // 如果时间为空则默认当前时间
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        if (dateStr != null && !dateStr.equals("")) {
            String time = "";
            try {
                Date dateTwo = format.parse(dateStr);
                time = format.format(dateTwo);
                date = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            String timeTwo = format.format(new Date());
            try {
                date = format.parse(timeTwo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }


    public static String yearOldVaule(String time, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.add(Calendar.YEAR, -year);
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
        return format0.format(cal.getTime());
    }
}
