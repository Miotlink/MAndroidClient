package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJJ on 2016/9/19.
 */

public class AvailableActivityRequest {

    @SerializedName("Token")
    String token;
    @SerializedName("ServiceTypeId")
    String serviceTypeId;

    public AvailableActivityRequest() {
    }

    public AvailableActivityRequest(String token, String serviceTypeId) {
        this.token = token;
        this.serviceTypeId = serviceTypeId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
}
