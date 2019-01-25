package com.homepaas.sls.domain.entity;

import java.io.Serializable;

/**
 * Created by CMJ on 2016/6/25.
 * 账户信息，账户余额等信息
 */

public class PayHistoryInfo implements Serializable {

    private boolean money;//余额
    private boolean aliPay;//支付宝
    private boolean wxPay;//微信

    public PayHistoryInfo() {
    }

    public PayHistoryInfo(boolean money, boolean aliPay, boolean wxPay) {
        this.money = money;
        this.aliPay = aliPay;
        this.wxPay = wxPay;
    }

    public boolean isMoney() {
        return money;
    }

    public void setMoney(boolean money) {
        this.money = money;
    }

    public boolean isAliPay() {
        return aliPay;
    }

    public void setAliPay(boolean aliPay) {
        this.aliPay = aliPay;
    }

    public boolean isWxPay() {
        return wxPay;
    }

    public void setWxPay(boolean wxPay) {
        this.wxPay = wxPay;
    }
}
