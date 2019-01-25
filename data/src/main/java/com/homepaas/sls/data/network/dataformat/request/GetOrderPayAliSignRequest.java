package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CMJ on 2016/6/25.
 */

public class GetOrderPayAliSignRequest {
    @SerializedName("Token")
    String token;
    @SerializedName("OrderId")
    String orderCode;
    @SerializedName("CouponId")
    String redEnvelopIds;
    @SerializedName("Alipay")
    String needPay;
    @SerializedName("BalancePay")
    String balancePay;

    public GetOrderPayAliSignRequest() {
    }

    public GetOrderPayAliSignRequest(String token, String orderCode, String redEnvelopIds, String needPay, String balancePay) {
        this.token = token;
        this.orderCode = orderCode;
        this.redEnvelopIds = redEnvelopIds;
        this.needPay = needPay;
        this.balancePay = balancePay;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getRedEnvelopIds() {
        return redEnvelopIds;
    }

    public void setRedEnvelopIds(String redEnvelopIds) {
        this.redEnvelopIds = redEnvelopIds;
    }

    public String getNeedPay() {
        return needPay;
    }

    public void setNeedPay(String needPay) {
        this.needPay = needPay;
    }

    public String getBalancePay() {
        return balancePay;
    }

    public void setBalancePay(String balancePay) {
        this.balancePay = balancePay;
    }
}
