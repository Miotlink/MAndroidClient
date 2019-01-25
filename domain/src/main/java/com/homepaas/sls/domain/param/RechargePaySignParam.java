package com.homepaas.sls.domain.param;

/**
 * on 2016/6/25 0025
 *
 * @author zhudongjie
 */
public class RechargePaySignParam {

    public String needPay;

    public String totalMoney;

    public String activity;

    public RechargePaySignParam(String needPay, String totalMoney, String activity) {
        this.needPay = needPay;
        this.totalMoney = totalMoney;
        this.activity = activity;
    }
}
