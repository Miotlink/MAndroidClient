package com.homepaas.sls.domain.param;

/**
 * Created by CMJ on 2016/6/28.
 */

public class GetOrderpayWxSignParams {
    public String orderCode;
    public String redEnvelopIds;
    public String needPay;
    public String balancePay;

    public GetOrderpayWxSignParams() {
    }

    public GetOrderpayWxSignParams(String orderCode, String redEnvelopIds, String needPay, String balancePay) {
        this.orderCode = orderCode;
        this.redEnvelopIds = redEnvelopIds;
        this.needPay = needPay;
        this.balancePay = balancePay;
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
