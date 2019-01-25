package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.embedded_class.Time;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CJJ on 2016/9/14.
 */

public class ServiceScheduleEntity implements Serializable {
    @SerializedName("Date")
    public String date;
    @SerializedName("TimeRange")
    public List<String> timeSchedule;
    @SerializedName("TimeRange2")
    public List<Time> timeList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTimeSchedule() {
        return timeSchedule;
    }

    public void setTimeSchedule(List<String> timeSchedule) {
        this.timeSchedule = timeSchedule;
    }

    public List<Time> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Time> timeList) {
        this.timeList = timeList;
    }
}
