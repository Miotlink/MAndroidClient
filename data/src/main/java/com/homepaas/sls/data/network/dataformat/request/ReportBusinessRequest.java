package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/6/20 0020
 *
 * @author zhudongjie
 */
public class ReportBusinessRequest {

    @SerializedName("Token")
    public final String token;

    @SerializedName("MerchantId")
    public final String businessId;

    public ReportBusinessRequest(String token, String businessId) {
        this.token = token;
        this.businessId = businessId;
    }
}
