package com.homepaas.sls.domain.entity.embedded_class;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CJJ on 2016/10/18.
 *
 */

public class Time implements Serializable{

    @SerializedName("Time")
    public String time;
    @SerializedName("IsVacant")
    public String available;

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isAvailable(){
        return "1".equals(available);
    }
}
