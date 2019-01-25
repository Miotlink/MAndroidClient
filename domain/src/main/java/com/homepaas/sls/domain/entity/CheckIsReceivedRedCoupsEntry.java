package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2017/3/8.
 */

public class CheckIsReceivedRedCoupsEntry implements Serializable{
    @SerializedName("IsReceived")  //0：没有领取，1：已经领取
    private String isReceived;
    @SerializedName("Url")   //跳转的url
    private String url;
    @SerializedName("Title")   //标题
    private String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(String isReceived) {
        this.isReceived = isReceived;
    }
}
