package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/2/22.
 */

public class GetAtStoreActivityRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("MerchantId")
    private String merchantId;

    public GetAtStoreActivityRequest(String token, String merchantId) {
        this.token = token;
        this.merchantId = merchantId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}

