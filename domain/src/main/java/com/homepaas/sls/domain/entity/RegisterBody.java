package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/1/21 0021
 *
 * @author zhudongjie .
 */
public class RegisterBody {

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

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
