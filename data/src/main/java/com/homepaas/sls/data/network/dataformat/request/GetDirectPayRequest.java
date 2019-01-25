package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/5.
 */
public class GetDirectPayRequest {

    @SerializedName("Token")
    String token;
    @SerializedName("ReceiverID")
    String receiverId;
    @SerializedName("ReceiverType")
    String receiverType;
    @SerializedName("PaySource")
    String paySource;
    @SerializedName("TotalMoney")
    String totalMoney;
    @SerializedName("ServiceTypeID")
    String serviceTypeID;

    public GetDirectPayRequest(String token, String receiverId, String receiverType, String paySource, String totalMoney, String serviceTypeID) {
        this.token=token;
        this.receiverId = receiverId;
        this.receiverType= receiverType;
        this.paySource = paySource;
        this.totalMoney = totalMoney;
        this.serviceTypeID = serviceTypeID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
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

    public String getServiceTypeID() {
        return serviceTypeID;
    }

    public void setServiceTypeID(String serviceTypeID) {
        this.serviceTypeID = serviceTypeID;
    }
}
