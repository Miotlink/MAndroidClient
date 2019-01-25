package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * on 2016/2/14 0014
 *
 * @author zhudongjie .
 */
public class LoginBody implements Serializable {

    @SerializedName("Token")
    private String token;

    @SerializedName("UserId")
    private String userId;

    //环信账户
    @SerializedName("ImUserName")
    private String imUserName;
    //环信密码
    @SerializedName("ImPwd")
    private String imPwd;

    //头像
    @SerializedName("SmallPic")
    private String smallPic;
//    是否是新用户，0:不是：1：是,
    @SerializedName("IsNewUser")
    private String newUser;

    // 新用户红包信息
    @SerializedName("NewUserCouponMessage")
    private String newUserCouponMessage;

    @SerializedName("AppViewId")
    private String appViewId;

//    h5登录后回传信息
    @SerializedName("PhoneNumber")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAppViewId() {
        return appViewId;
    }

    public void setAppViewId(String appViewId) {
        this.appViewId = appViewId;
    }

    public String getNewUserCouponMessage() {
        return newUserCouponMessage;
    }

    public void setNewUserCouponMessage(String newUserCouponMessage) {
        this.newUserCouponMessage = newUserCouponMessage;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public String getImUserName() {
        return imUserName;
    }

    public void setImUserName(String imUserName) {
        this.imUserName = imUserName;
    }

    public String getImPwd() {
        return imPwd;
    }

    public void setImPwd(String imPwd) {
        this.imPwd = imPwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
