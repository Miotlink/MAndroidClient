package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/17 0017
 *
 * @author zhudongjie .
 */
public class ResetPassword1Request {
    @SerializedName("Phone")
    public final String phone;
    @SerializedName("Captcha")
    public final String captcha;

    public ResetPassword1Request(String phone, String captcha) {
        this.phone = phone;
        this.captcha = captcha;
    }
}
