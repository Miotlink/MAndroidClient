package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/6/25 0025
 *
 * @author zhudongjie
 */
public class WeChatPaySign {

    @SerializedName("appid")
    private String appId;

    @SerializedName("partnerid")
    private String partnerId;

    @SerializedName("prepayid")
    private String prepayId;

    @SerializedName("package")
    private String packageExtend;

    @SerializedName("noncestr")
    private String nonceString;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("sign")
    private String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPackageExtend() {
        return packageExtend;
    }

    public void setPackageExtend(String packageExtend) {
        this.packageExtend = packageExtend;
    }

    public String getNonceString() {
        return nonceString;
    }

    public void setNonceString(String nonceString) {
        this.nonceString = nonceString;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
