package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sherily on 2016/9/16.
 */
public class ProviderDetailRequest {

    //待获取详情的对象 1 表示获取工人, 2 表示获取商户
    @SerializedName("Type")
    private String type;
    @SerializedName("Id")
    private String Id;
    @SerializedName("Token")
    private String token;

    public ProviderDetailRequest(String type, String id, String token) {
        this.type = type;
        Id = id;
        this.token = token;
    }
}
