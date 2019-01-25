package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/12/7.
 */

public class RechargeInfoRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("RechargeCode")
    private String rechargeCode;

    public RechargeInfoRequest() {
    }

    public RechargeInfoRequest(String token, String rechargeCode) {
        this.token = token;
        this.rechargeCode = rechargeCode;
    }

    public String getRechargeCode() {
        return rechargeCode;
    }

    public void setRechargeCode(String rechargeCode) {
        this.rechargeCode = rechargeCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
