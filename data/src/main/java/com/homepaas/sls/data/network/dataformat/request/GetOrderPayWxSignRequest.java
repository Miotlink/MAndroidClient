package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/6/30.
 */
public class GetOrderPayWxSignRequest {


    @SerializedName("Token")
    String token;
    @SerializedName("OrderId")
    String orderCode;
    @SerializedName("CouponId")
    String redEnvelopIds;
    @SerializedName("WxPay")//扣除优惠券优惠金额扣除余额金额后需要微信支付的金额
    String needPay;
    @SerializedName("BalancePay")
    String balancePay;

    public GetOrderPayWxSignRequest() {
    }

    public GetOrderPayWxSignRequest(String token, String orderCode, String redEnvelopIds, String needPay, String balancePay) {
        this.token = token;
        this.orderCode = orderCode;
        this.redEnvelopIds = redEnvelopIds;
        this.needPay = cutWithAccuracy(needPay);
        this.balancePay = balancePay;
    }

    /**
     * 针对微信的支付精度问题，例如18.00类似的金额只能提交18，不能有小数点，其他如18.03则可以
     * @param needPay
     * @return
     */
    private String cutWithAccuracy(String needPay) {
        if (needPay == null)
            return needPay;
        int dotIndex = needPay.indexOf(".");
        String smallNumber = needPay.substring(dotIndex+1);
        int tailPrice = Integer.parseInt(smallNumber);
        if (tailPrice==0)
            return needPay.substring(0,dotIndex);
        return needPay;
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
