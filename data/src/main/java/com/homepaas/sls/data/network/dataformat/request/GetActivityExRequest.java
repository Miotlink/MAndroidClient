package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/4/1.
 */

public class GetActivityExRequest {
    @SerializedName("Token")
    String token;
    @SerializedName("ServiceId")
    String serviceId;

    public GetActivityExRequest(String token, String serviceId) {
        this.token = token;
        this.serviceId = serviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
