package com.homepaas.sls.data.network.dataformat.request;

/**
 * Created by mhy on 2017/9/1.
 */

public class IodOfWxpayRequest {
    /**
     * Token：必填；
     * OrderId：必填；
     * BalancePay：必填；余额支付金额，大于等于0
     * Wxpay：必填；微信支付金额，大于0
     * Token : xxxxxx
     * OrderId : xxxxxx
     * BalancePay : 30
     * Wxpay : 10
     */

    private String Token;
    private String OrderId;
    private String BalancePay;
    private String Wxpay;

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

    public String getWxpay() {
        return Wxpay;
    }

    public void setWxpay(String Wxpay) {
        this.Wxpay = Wxpay;
    }
}
