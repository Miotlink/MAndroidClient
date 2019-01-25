package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/14 0014
 *
 * @author zhudongjie .
 */
public class DeleteMessageRequest {
    @SerializedName("Token")
    public final String token;
    @SerializedName("Ids")
    public final String[] ids;

    public DeleteMessageRequest(String token, String... ids) {
        this.token = token;
        this.ids = ids;
    }
}
