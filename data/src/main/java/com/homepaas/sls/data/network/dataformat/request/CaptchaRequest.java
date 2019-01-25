package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/2 0002
 *
 * @author zhudongjie .
 */
public class CaptchaRequest {

    @SerializedName("Phone")
    public final String phone;

    /**
     * 1.register
     * 2.reset password
     * 3.login
     */
    @SerializedName("Type")
    public final int type;

    public CaptchaRequest(String phone, int type) {
        this.phone = phone;
        this.type = type;
    }
}
