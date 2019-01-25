package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/2/13.
 */

public class MerchantServicePriceListRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("Id")
    private String id;

    public MerchantServicePriceListRequest(String token, String id) {
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
