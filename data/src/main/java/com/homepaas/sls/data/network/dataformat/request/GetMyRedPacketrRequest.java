package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/4/28.
 */
public class GetMyRedPacketrRequest {

    @SerializedName("Token")
    String token;
    @SerializedName("EnvelopeStatus")
    String status;

    public GetMyRedPacketrRequest() {
    }

    public GetMyRedPacketrRequest(String token, String status) {
        this.token = token;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
