package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/25 0025
 *
 * @author zhudongjie .
 */
public class CheckCallableRequest {

    /**
     * 1.工人 2.商户
     */
    @SerializedName("CallType")
    public final int callType;

    @SerializedName("Id")
    public final String id;

    @SerializedName("Phone")
    public final String phoneNumber;

    @SerializedName("DeviceId")
    public final String deviceId;


    public CheckCallableRequest(int callType, String id, String phoneNumber, String deviceId) {
        this.callType = callType;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.deviceId = deviceId;
    }
}
