package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/1.
 *
 */
public class DeleteOrderRequest {

    @SerializedName("Token")
    String token;
    @SerializedName("OrderId")
    String orderId;

    public DeleteOrderRequest() {
    }

    public DeleteOrderRequest(String token, String orderId) {
        this.token = token;
        this.orderId = orderId;
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
}
