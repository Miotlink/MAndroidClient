package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/6/30 0030
 *
 * @author zhudongjie
 */
public class EvaluateOrderRequest {

    @SerializedName("Token")
    public final String token;
    @SerializedName("OrderId")
    public final String orderId;
    @SerializedName("Score")
    public final String score;
    @SerializedName("Content")
    public final String content;

    public EvaluateOrderRequest(String token, String orderId, String score, String content) {
        this.token = token;
        this.orderId = orderId;
        this.score = score;
        this.content = content;
    }
}
