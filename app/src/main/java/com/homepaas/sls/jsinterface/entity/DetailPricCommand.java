package com.homepaas.sls.jsinterface.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/6.
 */

public class DetailPricCommand {
    @SerializedName("serviceId")
    private String serviceId;
    @SerializedName("lng")
    private String lng;
    @SerializedName("lat")
    private String lat;

    public DetailPricCommand(String serviceId, String lng, String lat) {
        this.serviceId = serviceId;
        this.lng = lng;
        this.lat = lat;
    }
}
