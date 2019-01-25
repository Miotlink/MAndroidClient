package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/5/3.
 */
public class GetAllOrderRequest {

    @SerializedName("Token")
    String token;
    @SerializedName("PageIndex")
    String index;
    @SerializedName("PageSize")
    String size;

    public GetAllOrderRequest() {
    }

    public GetAllOrderRequest(String token, String index, String size) {
        this.token = token;
        this.index = index;
        this.size = size;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
