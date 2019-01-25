package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/12/6.
 */

public class AccountDetailsRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("Id")
    private int id;

    public AccountDetailsRequest(String token, int id) {
        this.token = token;
        this.id = id;
    }

    public AccountDetailsRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
