package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/7.
 */
public class DirectBalancePayRequest {

    @SerializedName("Token")
    String token;
    @SerializedName("DirectID")
    String directPayId;

    public DirectBalancePayRequest(String token, String directPayId) {

        this.token = token;
        this.directPayId = directPayId;
    }

    public String getToken() {
        return token;
    }

    public String getDirectPayId() {
        return directPayId;
    }
}
