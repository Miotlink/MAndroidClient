package com.homepaas.sls.domain.entity;

/**
 * Created by Administrator on 2015/12/23.
 *
 */

public class PersonalInfo {

    private String userId;
    private String smallPic;
    private String hqPic;
    private String nickName;
    private String account;
    private String phoneNumber;
    private String qrCode;
    private String recommendationUrl_Mine;
    private String recommendationUrl_Add;
    private String recommendationUrl_Share;
    private String recommendUserName;
    private String recommendationCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecommendUserName() {
        return recommendUserName;
    }

    public void setRecommendUserName(String recommendUserName) {
        this.recommendUserName = recommendUserName;
    }

    public String getRecommendationCode() {
        return recommendationCode;
    }

    public void setRecommendationCode(String recommendationCode) {
        this.recommendationCode = recommendationCode;
    }

    public String getRecommendationUrl_Mine() {
        return recommendationUrl_Mine;
    }

    public void setRecommendationUrl_Mine(String recommendationUrl_Mine) {
        this.recommendationUrl_Mine = recommendationUrl_Mine;
    }

    public String getRecommendationUrl_Add() {
        return recommendationUrl_Add;
    }

    public void setRecommendationUrl_Add(String recommendationUrl_Add) {
        this.recommendationUrl_Add = recommendationUrl_Add;
    }

    public String getRecommendationUrl_Share() {
        return recommendationUrl_Share;
    }

    public void setRecommendationUrl_Share(String recommendationUrl_Share) {
        this.recommendationUrl_Share = recommendationUrl_Share;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public String getHqPic() {
        return hqPic;
    }

    public void setHqPic(String hqPic) {
        this.hqPic = hqPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PersonalInfo{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", smallPic='").append(smallPic).append('\'');
        sb.append(", hqPic='").append(hqPic).append('\'');
        sb.append(", nickName='").append(nickName).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", qrCode='").append(qrCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
