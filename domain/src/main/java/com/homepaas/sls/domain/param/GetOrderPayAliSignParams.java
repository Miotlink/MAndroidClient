package com.homepaas.sls.domain.param;

/**
 * Created by CMJ on 2016/6/25.
 * 获取下单支付支付宝支付的签名信息 参数
 */

public class GetOrderPayAliSignParams {
    public String orderCode;
    public String redEnvelopIds;
    public String balancePay;
    public String needPay;//支付宝支付的金额

    public GetOrderPayAliSignParams() {
    }

    public GetOrderPayAliSignParams(String orderCode, String redEnvelopIds, String needPay, String balancePay) {
        this.orderCode = orderCode;
        this.redEnvelopIds = redEnvelopIds;
        this.balancePay = balancePay;
        this.needPay = needPay;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setRedEnvelopIds(String redEnvelopIds) {
        this.redEnvelopIds = redEnvelopIds;
    }

    public void setBalancePay(String balancePay) {
        this.balancePay = balancePay;
    }

    public void setNeedPay(String needPay) {
        this.needPay = needPay;
    }
}
