package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/6/17.
 */
public class GetOrderDetailRequest {

    @SerializedName("Token")
    String token;
    @SerializedName("OrderId")
    String orderId;

    public GetOrderDetailRequest() {
    }

    public GetOrderDetailRequest(String token, String orderCode) {
        this.token = token;
        this.orderId = orderCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getorderCode() {
        return orderId;
    }

    public void setorderCode(String orderCode) {
        this.orderId = orderCode;
    }
}
