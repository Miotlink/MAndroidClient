package com.homepaas.sls.domain.entity;

/**
 * Created by mhy on 2017/11/14.
 * 时间有变或者id有变，需要充值每天弹框的次数
 */

public class NewFirstOpenAppInfo {

    //Id (string, optional): 标识
    //    Title (string, optional): 标题
//    ImageUrl (string, optional): 弹框的图片
//    OpenUrl (string, optional): 点击图片后跳转的Url
//    DayOfTimes (string, optional): 每天弹窗的次数 -1:表示无限次
//    IsShare (string, optional): 是否分享,0：不分享。1：分享，默认不分享
//    ShareUrl (string, optional): 分享的url
//    ShareTitle (string, optional): 分享的标题
//    ShareDescription (string, optional): 分享的描述
//    SharePic (string, optional): 分享的图片的Url
    private String Title;
    private String ImageUrl;
    private String OpenUrl;
    private int DayOfTimes;
    private String IsShare;
    private String ShareUrl;
    private String ShareTitle;
    private String ShareDescription;
    private String SharePic;
    private String Id;
    private String actionTime;
    private int currentDialogShowNum;//当前已经弹框多少次  -1表示无限次

    public int getCurrentDialogShowNum() {
        return currentDialogShowNum;
    }

    public void setCurrentDialogShowNum(int currentDialogShowNum) {
        this.currentDialogShowNum = currentDialogShowNum;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public String getOpenUrl() {
        return OpenUrl;
    }

    public void setOpenUrl(String OpenUrl) {
        this.OpenUrl = OpenUrl;
    }

    public int getDayOfTimes() {
        return DayOfTimes;
    }

    public void setDayOfTimes(int DayOfTimes) {
        this.DayOfTimes = DayOfTimes;
    }

    public String getIsShare() {
        return IsShare;
    }

    public void setIsShare(String IsShare) {
        this.IsShare = IsShare;
    }

    public String getShareUrl() {
        return ShareUrl;
    }

    public void setShareUrl(String ShareUrl) {
        this.ShareUrl = ShareUrl;
    }

    public String getShareTitle() {
        return ShareTitle;
    }

    public void setShareTitle(String ShareTitle) {
        this.ShareTitle = ShareTitle;
    }

    public String getShareDescription() {
        return ShareDescription;
    }

    public void setShareDescription(String ShareDescription) {
        this.ShareDescription = ShareDescription;
    }

    public String getSharePic() {
        return SharePic;
    }

    public void setSharePic(String SharePic) {
        this.SharePic = SharePic;
    }

}
