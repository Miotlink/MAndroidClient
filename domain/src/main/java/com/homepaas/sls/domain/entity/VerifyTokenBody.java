package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sherily on 2017/3/13.
 */

public class VerifyTokenBody {
    @SerializedName("UserId")
    private String userId;
    //环信账户
    @SerializedName("ImUserName")
    private String imUserName;
    //环信密码
    @SerializedName("ImPwd")
    private String imPwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
