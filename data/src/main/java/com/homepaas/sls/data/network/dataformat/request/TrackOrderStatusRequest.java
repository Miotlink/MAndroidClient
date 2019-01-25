package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/7/19.
 */

public class TrackOrderStatusRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("OrderId")
    private String orderId;

    public TrackOrderStatusRequest(String token, String orderId) {
        this.token = token;
        this.orderId = orderId;
    }
}
