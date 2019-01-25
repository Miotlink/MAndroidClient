package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/6.
 */
public class GetDirectPayAliSignRequest {

    @SerializedName("Token")
    String token;
    @SerializedName("DirectID")
    String directPayId;
    @SerializedName("BalancePay")
    String balancePayMoney;
    public GetDirectPayAliSignRequest(String token, String directPayId, String balancePayMoney) {
        this.token = token;
        this.directPayId = directPayId;
        this.balancePayMoney = balancePayMoney;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDirectPayId() {
        return directPayId;
    }

    public void setDirectPayId(String directPayId) {
        this.directPayId = directPayId;
    }

    public String getBalancePayMoney() {
        return balancePayMoney;
    }

    public void setBalancePayMoney(String balancePayMoney) {
        this.balancePayMoney = balancePayMoney;
    }
}
