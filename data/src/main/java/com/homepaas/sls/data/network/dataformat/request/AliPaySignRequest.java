package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/12/6.
 */

public class AliPaySignRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("NeedPay")
    private String needPay;
    @SerializedName("TotalMoney")
    private String totalPay;
    @SerializedName("Activity")
    private String activity;
    @SerializedName("ActivityId")
    private String activityId;


    public AliPaySignRequest(String token, String activityId, String needPay, String totalPay, String activity) {
        this.token = token;
        this.needPay = needPay;
        this.totalPay = totalPay;
        this.activity = activity;
        this.activityId = activityId;
    }

    public AliPaySignRequest() {
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNeedPay() {
        return needPay;
    }

    public void setNeedPay(String needPay) {
        this.needPay = needPay;
    }

    public String getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(String totalPay) {
        this.totalPay = totalPay;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
