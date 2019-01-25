package com.homepaas.sls.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/12/23.
 *
 */

public class PersonalInfoModel implements Parcelable{

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

    public PersonalInfoModel() {
    }

    protected PersonalInfoModel(Parcel in) {
        userId=in.readString();
        smallPic = in.readString();
        hqPic = in.readString();
        nickName = in.readString();
        account = in.readString();
        phoneNumber = in.readString();
        qrCode = in.readString();
        recommendationUrl_Mine = in.readString();
        recommendationUrl_Add = in.readString();
        recommendationUrl_Share = in.readString();
        recommendUserName = in.readString();
        recommendationCode = in.readString();
    }

    public static final Creator<PersonalInfoModel> CREATOR = new Creator<PersonalInfoModel>() {
        @Override
        public PersonalInfoModel createFromParcel(Parcel in) {
            return new PersonalInfoModel(in);
        }

        @Override
        public PersonalInfoModel[] newArray(int size) {
            return new PersonalInfoModel[size];
        }
    };

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(smallPic);
        dest.writeString(hqPic);
        dest.writeString(nickName);
        dest.writeString(account);
        dest.writeString(phoneNumber);
        dest.writeString(qrCode);
        dest.writeString(recommendationUrl_Mine);
        dest.writeString(recommendationUrl_Add);
        dest.writeString(recommendationUrl_Share);
        dest.writeString(recommendUserName);
        dest.writeString(recommendationCode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PersonalInfoModel{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(",smallPic='").append(smallPic).append('\'');
        sb.append(", hqPic='").append(hqPic).append('\'');
        sb.append(", nickName='").append(nickName).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", qrCode='").append(qrCode).append('\'');
        sb.append(", recommendationUrl_Mine='").append(recommendationUrl_Mine).append('\'');
        sb.append(", recommendationUrl_Add='").append(recommendationUrl_Add).append('\'');
        sb.append(", recommendationUrl_Share='").append(recommendationUrl_Share).append('\'');
        sb.append(", recommendUserName='").append(recommendUserName).append('\'');
        sb.append(", recommendationCode='").append(recommendationCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
