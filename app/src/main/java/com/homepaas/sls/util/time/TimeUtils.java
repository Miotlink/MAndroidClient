package com.homepaas.sls.util.time;

/**
 * Created by Jin.Wu on 2017/4/25.
 */

public class TimeUtils {

    public static String secToTime(int time) {
        String timeStr = null;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
//            return "00'00''";
            return "00:00";
        } else {
            if (time > 86400) {
                day = time / 86400;
                time = time % 86400;
            }
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
//                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
                timeStr = unitFormat(minute) + "'" + unitFormat(second) + "''";
            } else {
                hour = minute / 60;
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + "'" + unitFormat(minute) + "''" + unitFormat(second) + "''";
//                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        if (day > 0) {
            timeStr = day + "天" + timeStr;
        }
        return timeStr;
    }

    public static String secToTime2(int time) {
        String timeStr = null;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00";
        } else {
            if (time > 86400) {
                day = time / 86400;
                time = time % 86400;
            }
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        if (day > 0) {
            timeStr = day + "天" + timeStr;
        }
        return timeStr;
    }

    public static String minuteToTime(int time) {
        String timeStr = null;
        int day = 0;
        int hour = 0;
        int minute = 0;
        if (time <= 0)
            timeStr = "0分钟";
        else {
            hour = time / 60;
            if (hour == 0) {
                minute = time % 60;
                timeStr = new StringBuffer().append(minute).append("分钟").toString();
            } else if (hour < 24) {
                minute = time % 60;
                if (minute == 0)
                    timeStr = new StringBuffer().append(hour).append("小时").toString();
                else
                    timeStr = new StringBuffer().append(hour).append("小时").append(minute).append("分钟").toString();
            } else {
                day = hour / 24;
                hour = hour % 24;
                minute = time % 60;
                StringBuffer stringBuffer = new StringBuffer().append(day).append("天");
                if (hour != 0)
                    stringBuffer.append(hour).append("小时");
                if (minute != 0)
                    stringBuffer.append(minute).append("分钟");

                timeStr = stringBuffer.toString();
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
