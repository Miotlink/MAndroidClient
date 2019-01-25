package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sheirly on 2016/9/10.
 *
 * AppViewPage，WebViewDetail，OutterLink这三个对象同时只有一个不为null，由LinkType决定哪个不为空
 * 2是WebViewDetail，1是AppViewPage，3是OutterLink
 *
 */

public class BannerInfo {

    @SerializedName("Id")
    private String id;

    @SerializedName("ImageUrl")
    private String imageUrl;

    @SerializedName("ObjectApns")
    private PushInfo apns;

    /**
     * 2是WebViewDetail，1是AppViewPage，3是OutterLink
     */
    @SerializedName("LinkType")
    private String linkType;

    @SerializedName("DetailUrl")
    private String detailUrl;



    @SerializedName("WebViewDetail")
    private WebViewDetail webViewDetail;

    @SerializedName("AppViewPage")
    private AppViewPage appViewPage;

//    @SerializedName("OutterLink")
//    private OutterLink outterLink;

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public WebViewDetail getWebViewDetail() {
        return webViewDetail;
    }

    public void setWebViewDetail(WebViewDetail webViewDetail) {
        this.webViewDetail = webViewDetail;
    }

    public AppViewPage getAppViewPage() {
        return appViewPage;
    }

    public void setAppViewPage(AppViewPage appViewPage) {
        this.appViewPage = appViewPage;
    }

    public PushInfo getApns() {
        return apns;
    }
}
