package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/7/6.
 */

public class ExpressDetailRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("OrderId")
    private String orderId;

    public ExpressDetailRequest(String token, String orderId) {
        this.token = token;
        this.orderId = orderId;
    }
}
