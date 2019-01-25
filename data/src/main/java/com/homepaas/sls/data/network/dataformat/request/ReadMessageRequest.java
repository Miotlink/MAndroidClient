package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/18 0018
 *
 * @author zhudongjie .
 */
public class ReadMessageRequest {
    @SerializedName("Token")
    public final String token;
    @SerializedName("Ids")
    public final String[] ids;

    public ReadMessageRequest(String token, String... ids) {
        this.token = token;
        this.ids = ids;
    }
}
