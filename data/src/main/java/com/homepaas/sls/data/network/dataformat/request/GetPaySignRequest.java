package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/6/25 0025
 *
 * @author zhudongjie
 */
public class GetPaySignRequest {

    @SerializedName("Token")
    public final String token;

    @SerializedName("NeedPay")
    public final String needPay;

    @SerializedName("TotalMoney")
    public final String totalMoney;

    @SerializedName("Activity")
    public final String activity;

    public GetPaySignRequest(String token, String needPay, String totalMoney, String activity) {
        this.token = token;
        this.needPay = needPay;
        this.totalMoney = totalMoney;
        this.activity = activity;
    }
}
