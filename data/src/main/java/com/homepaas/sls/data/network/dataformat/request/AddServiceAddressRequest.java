package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.param.AddServiceAddressParam;

/**
 * Created by CJJ on 2016/9/13.
 */

public class AddServiceAddressRequest {
    @SerializedName("Token")
    String token;
    @SerializedName("Address")
    AddressEntity param;

    public AddServiceAddressRequest(String token, AddressEntity param) {
        this.token = token;
        this.param = param;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AddressEntity getParam() {
        return param;
    }

    public void setParam(AddressEntity param) {
        this.param = param;
    }
}
