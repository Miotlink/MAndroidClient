package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sheirly on 2016/6/20.
 */
public class GetMyRedEnvelopeRequest {

    @SerializedName("Token")
    String token;
    @SerializedName("EnvelopeStatus")
    String envelopeStatus;

    public GetMyRedEnvelopeRequest() {
    }

    public GetMyRedEnvelopeRequest(String token, String envelopeStatus) {
        this.token = token;
        this.envelopeStatus = envelopeStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEnvelopeStatus() {
        return envelopeStatus;
    }

    public void setEnvelopeStatus(String envelopeStatus) {
        this.envelopeStatus = envelopeStatus;
    }
}
