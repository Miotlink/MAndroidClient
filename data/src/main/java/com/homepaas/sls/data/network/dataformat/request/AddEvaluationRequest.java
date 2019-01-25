package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/6/24 0024
 *
 * @author zhudongjie
 */
public class AddEvaluationRequest {

    @SerializedName("Token")
    private String token;
    @SerializedName("OwnerId")
    private String ownerId;
    @SerializedName("Score")
    private String score;
    @SerializedName("Content")
    private String content;
    @SerializedName("OwnerType")
    private String ownerType;
    @SerializedName("OrderId")
    private String orderId;

    public AddEvaluationRequest() {
    }

    public AddEvaluationRequest(String token, String ownerId, String score, String content, String ownerType, String orderId) {
        this.token = token;
        this.ownerId = ownerId;
        this.score = score;
        this.content = content;
        this.ownerType = ownerType;
        this.orderId = orderId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
