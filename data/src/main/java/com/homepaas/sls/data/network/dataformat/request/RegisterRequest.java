package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/1 0001
 *
 * @author zhudongjie .
 */

public class RegisterRequest {
    @SerializedName("LoginName")
    public final String loginName;
    @SerializedName("Password")
    public final String password;
    @SerializedName("Captcha")
    public final String captcha;
    @SerializedName("RecommendationCode")
    public final String recommendationCode;
    /**
     * 返回类型
     * 0:不返回用户标识
     * 1:返回用户标识
     * 默认:0
     */
    @SerializedName("Type")
    public final int type;
    @SerializedName("DeviceId")
    public final String deviceId;

    public RegisterRequest(String loginName, String password, String captcha, int type, String deviceId, String recommendationCode) {
        this.loginName = loginName;
        this.password = password;
        this.captcha = captcha;
        this.type = type;
        this.deviceId = deviceId;
        this.recommendationCode = recommendationCode;
    }

}
