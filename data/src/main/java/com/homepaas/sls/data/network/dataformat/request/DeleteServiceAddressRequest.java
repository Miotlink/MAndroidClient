package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJJ on 2016/9/13.
 * 删除我的服务地址
 */

public class DeleteServiceAddressRequest {

    @SerializedName("AddressId")
    public String addressId;
    @SerializedName("Token")
    public String token;

    public DeleteServiceAddressRequest() {
    }

    public DeleteServiceAddressRequest(String token, String addressId) {
        this.token = token;
        this.addressId = addressId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
