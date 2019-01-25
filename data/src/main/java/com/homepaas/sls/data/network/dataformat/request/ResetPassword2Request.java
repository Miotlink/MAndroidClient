package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/18 0018
 *
 * @author zhudongjie .
 */
public class ResetPassword2Request {

    @SerializedName("Phone")
    public final String phone;

    @SerializedName("Captcha")
    public final String captcha;

    @SerializedName("Password")
    public final String password;

    @SerializedName("DeviceId")
    public final String deviceId;

    public ResetPassword2Request(String phone, String captcha, String password, String deviceId) {
        this.phone = phone;
        this.captcha = captcha;
        this.password = password;
        this.deviceId = deviceId;
    }
}
