package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/22 0022
 *
 * @author zhudongjie .
 */
public class FeedbackRequest {
    @SerializedName("Token")
    public final String token;
    @SerializedName("Content")
    public final String content;

    public FeedbackRequest(String token, String content) {
        this.token = token;
        this.content = content;
    }
}
