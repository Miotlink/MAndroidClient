package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sherily on 2017/6/6.
 */

public class CaptchaBody {
    @SerializedName("CountIp")
    private String countIp;
    @SerializedName("CountPhone")
    private String countPhone;

    public String getCountIp() {
        return countIp;
    }

    public void setCountIp(String countIp) {
        this.countIp = countIp;
    }

    public String getCountPhone() {
        return countPhone;
    }

    public void setCountPhone(String countPhone) {
        this.countPhone = countPhone;
    }
}
