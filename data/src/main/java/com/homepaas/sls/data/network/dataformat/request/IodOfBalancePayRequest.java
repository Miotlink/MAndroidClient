package com.homepaas.sls.data.network.dataformat.request;

/**
 * Created by mhy on 2017/9/1.
 */

public class IodOfBalancePayRequest {
    /**
     * Token：必填；
     * OrderId：必填；
     * BalancePay：必填；支付金额
     * Token : xxxxxx
     * OrderId : xxxxxx
     * BalancePay : 30
     */

    private String Token;
    private String OrderId;
    private String BalancePay;

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public String getBalancePay() {
        return BalancePay;
    }

    public void setBalancePay(String BalancePay) {
        this.BalancePay = BalancePay;
    }
}
