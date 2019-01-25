package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sherily on 2016/10/17.
 */

public class AddRecommendInfoRequest {

    @SerializedName("Token")
    private String token;

    //推荐码，Nullable
    @SerializedName("Code")
    private String code;

    public AddRecommendInfoRequest(String token, String code) {
        this.token = token;
        this.code = code;
    }

    public AddRecommendInfoRequest(String token) {
        this.token = token;
    }

    public AddRecommendInfoRequest() {
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
