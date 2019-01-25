package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/1.
 */
public class CancelOrderRequest {
    @SerializedName("Token")
    String token;
    @SerializedName("OrderId")
    String orderId;
    @SerializedName("CancelReason")
    String cancelReason;

    public CancelOrderRequest() {
    }

    public CancelOrderRequest(String token, String orderId, String cancelReason) {
        this.token = token;
        this.orderId = orderId;
        this.cancelReason = cancelReason;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}


