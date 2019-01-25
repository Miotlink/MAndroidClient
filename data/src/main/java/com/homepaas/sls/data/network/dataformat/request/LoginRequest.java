package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/14 0014
 *
 * @author zhudongjie .
 */
public class LoginRequest {
    @SerializedName("LoginName")
    public final String loginName;
    @SerializedName("Password")
    public final String password;
    @SerializedName("Captcha")
    public final String captcha;
    @SerializedName("DeviceId")
    public final String deviceId;

    public LoginRequest(String loginName, String password, String captcha, String deviceId) {
        this.loginName = loginName;
        this.password = password;
        this.captcha = captcha;
        this.deviceId = deviceId;
    }

    public LoginRequest(String loginName, String captcha, String deviceId) {
        this.loginName = loginName;
        this.captcha = captcha;
        this.deviceId = deviceId;
        this.password = null;
    }
}
