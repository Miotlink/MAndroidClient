package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sheirly on 2016/6/23.
 */
public class GetCouponContentsRequest {
    @SerializedName("Token")
    private String token;
//    （如果ServiceId有值则获取属性对应的红包）
    @SerializedName("ServiceId")
    private String serviceId;
    @SerializedName("Longitude")
    private String longitude;
    @SerializedName("Latitude")
    private String latitude;
    @SerializedName("IsPay")
    private String ispay;

    public String getIspay() {
        return ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

    public GetCouponContentsRequest(String token, String serviceId, String longitude, String latitude,String ispay) {
        this.token = token;
        this.serviceId = serviceId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.ispay = ispay;
    }

    public GetCouponContentsRequest(String token) {
        this.token = token;
    }
}
