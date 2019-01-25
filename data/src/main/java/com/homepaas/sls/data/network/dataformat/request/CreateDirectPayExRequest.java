package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/2/17.
 */

public class CreateDirectPayExRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("ReceiverID")  //接受者的Id, 如商户的Id
    private String receiverID;
    @SerializedName("ReceiverType")  //接受者的类型，如 商户为3
    private String receiverType;
    @SerializedName("PaySource")  //支付来源，IOS为0，Android为1
    private String paySource;
    @SerializedName("TotalMoney")  //支付总金额
    private String totalMoney;
    @SerializedName("ActivityNgId")  //活动的Id,如果没有就为NULL,如果 有多个活动，以逗号分开，如：“658,745”
    private String activityNgId;

    public CreateDirectPayExRequest(String token, String receiverID, String receiverType, String paySource, String totalMoney, String activityNgId) {
        this.token = token;
        this.receiverID = receiverID;
        this.receiverType = receiverType;
        this.paySource = paySource;
        this.totalMoney = totalMoney;
        this.activityNgId = activityNgId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getPaySource() {
        return paySource;
    }

    public void setPaySource(String paySource) {
        this.paySource = paySource;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getActivityNgId() {
        return activityNgId;
    }

    public void setActivityNgId(String activityNgId) {
        this.activityNgId = activityNgId;
    }
}
