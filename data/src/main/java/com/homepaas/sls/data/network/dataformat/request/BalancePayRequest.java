package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/5.
 */
public class BalancePayRequest {

    @SerializedName("Token")
    String token;
    @SerializedName("OrderId")
    String orderId;
    @SerializedName("CouponId")
    String couponId;
    @SerializedName("BalancePay")
    String balanceMoney;

    public BalancePayRequest(String token, String orderId, String couponId, String balanceMoney) {

        this.token = token;
        this.orderId = orderId;
        this.couponId = couponId;
        this.balanceMoney = balanceMoney;
    }

    public BalancePayRequest() {
    }

    public String getToken() {
        return token;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCouponId() {
        return couponId;
    }

    public String getBalanceMoney() {
        return balanceMoney;
    }
}
