package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/7/6.
 */

public class KdApiSearchResponseTrace {
    //时间
    @SerializedName("AcceptTime")
    private String acceptTime;
    //状态
    @SerializedName("AcceptStation")
    private String acceptStation;
    //备注
    @SerializedName("Remark")
    private String remark;

    public KdApiSearchResponseTrace() {
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
