package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/1/29 0029
 *
 * @author zhudongjie .
 */
public class BusinessInfoRequest {
    @SerializedName("BusinessId")
    public final String businessId;
    @SerializedName("Token")
    public final String token;

    public BusinessInfoRequest(String businessId, String token) {
        this.businessId = businessId;
        this.token = token;
    }
}
