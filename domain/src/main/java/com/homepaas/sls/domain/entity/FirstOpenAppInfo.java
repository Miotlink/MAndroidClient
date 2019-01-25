package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/12.
 *
 */
public class FirstOpenAppInfo implements Serializable{
//    Title (string, optional): 标题
//    ImageUrl (string, optional): 弹框的图片
//    OpenUrl (string, optional): 点击图片后跳转的Url
//    DayOfTimes (string, optional): 每天弹窗的次数 -1:表示无限次
//    IsShare (string, optional): 是否分享,0：不分享。1：分享，默认不分享
//    ShareUrl (string, optional): 分享的url
//    ShareTitle (string, optional): 分享的标题
//    ShareDescription (string, optional): 分享的描述
//    SharePic (string, optional): 分享的图片的Url

    @SerializedName("Title")
    String title;
    @SerializedName("Url")
    String url;
    @SerializedName("IsShare")
    String isShare;
    @SerializedName("ShareUrl")
    String shareUrl;
    @SerializedName("ShareTitle")
    String shareTitle;
    @SerializedName("SharePic")
    String sharePic;
    @SerializedName("ShareDescription")
    String shareDescription;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getShareDescription() {
        return shareDescription;
    }

    public void setShareDescription(String shareDescription) {
        this.shareDescription = shareDescription;
    }
}
