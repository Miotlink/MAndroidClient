package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/7/24.
 */

public class SubmitEvaluationRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("OrderCode")
    private String orderCode;
    //星级  填1-5
    @SerializedName("StarLevel")
    private String starLevel;
    @SerializedName("Content")
    private String content;
    @SerializedName("EvaluatedTag")
    private String evaluatedTag;

    public SubmitEvaluationRequest(String token, String orderCode, String starLevel, String content, String evaluatedTag) {
        this.token = token;
        this.orderCode = orderCode;
        this.starLevel = starLevel;
        this.content = content;
        this.evaluatedTag = evaluatedTag;
    }
}
