package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/7/1 0001
 *
 * @author zhudongjie
 */
public class GetShareInfoRequest {

    @SerializedName("Token")
    private String  token;
    @SerializedName("Id")
    private String  id;

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
