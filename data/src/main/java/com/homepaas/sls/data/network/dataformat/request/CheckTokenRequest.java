package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/3/2 0002
 *
 * @author zhudongjie .
 */
public class CheckTokenRequest {

    @SerializedName("Phone")
    public final String phone;

    @SerializedName("Token")
    public final String token;


    public CheckTokenRequest(String phone, String token) {
        this.phone = phone;
        this.token = token;
    }
}
