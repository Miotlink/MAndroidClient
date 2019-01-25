package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/5/9 0009
 *
 * @author zhudongjie
 */
public class PushServiceIdRequest {

    @SerializedName("Token")
    public final String token;

    @SerializedName("PushDeviceId")
    public final String pushDeviceId;

    @SerializedName("DeviceType")
    public final String deviceType = "0";// 0 means android

    public PushServiceIdRequest(String token, String pushDeviceId) {
        this.token = token;
        this.pushDeviceId = pushDeviceId;
    }


}
