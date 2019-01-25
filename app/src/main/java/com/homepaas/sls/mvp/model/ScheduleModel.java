package com.homepaas.sls.mvp.model;

import com.homepaas.sls.common.TimeUtils;

/**
 * Created by CJJ on 2016/9/14.
 *
 */

public class ScheduleModel {

    String timestamp;
    String formatTime;

    public ScheduleModel(String timestamp) {
        this.timestamp = timestamp;
        this.formatTime = TimeUtils.formatTime(timestamp);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }
}
