package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/8/14.
 */

public class FirstOpenAppInfoEx {
    // 标题
    @SerializedName("Title")
    private String title;
    // 弹框的图片
    @SerializedName("ImageUrl")
    private String imageUrl;
    // 点击图片后跳转的Url
    @SerializedName("OpenUrl")
    private String openUrl;
    // 是否分享,0：不分享。1：分享，默认不分享
    @SerializedName("IsShare")
    private String isShare;
    // 分享的url
    @SerializedName("ShareUrl")
    private String shareUrl;
    // 分享的标题
    @SerializedName("ShareTitle")
    private String shareTitle;
    // 分享的描述
    @SerializedName("ShareDescription")
    private String shareDescription;
    // 分享的图片的Url
    @SerializedName("SharePic")
    private String sharePic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDescription() {
        return shareDescription;
    }

    public void setShareDescription(String shareDescription) {
        this.shareDescription = shareDescription;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }
}
