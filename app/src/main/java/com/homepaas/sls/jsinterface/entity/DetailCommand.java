package com.homepaas.sls.jsinterface.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/31.
 */

public class DetailCommand {
    @SerializedName("Token")
    private String token;
    @SerializedName("ServiceId")
    private String serviceId;

    @SerializedName("PhoneNumber")
    private String phoneNumber;

    public DetailCommand(String token, String serviceId, String phoneNumber) {
        this.token = token;
        this.serviceId = serviceId;
        this.phoneNumber = phoneNumber;
    }
}
