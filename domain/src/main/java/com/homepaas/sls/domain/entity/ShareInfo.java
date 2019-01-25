package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/7/1 0001
 *
 * @author zhudongjie
 */
public class ShareInfo {

    @SerializedName("Url")
    private String url;
    @SerializedName("Picture")
    private String picture;
    @SerializedName("ContentType")
    private String contentType;
    @SerializedName("Title")
    private String title;
    @SerializedName("Description")
    private String description;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
