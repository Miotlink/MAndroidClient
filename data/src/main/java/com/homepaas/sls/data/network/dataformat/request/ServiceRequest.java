package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/1/29 0029
 *
 * @author zhudongjie .
 */
public class ServiceRequest {

    @SerializedName("Latitude")
    public final String latitude;

    @SerializedName("Longitude")
    public final String longitude;

    @SerializedName("Type")
    public final String type;

    @SerializedName("QueryStr")
    public final String queryStr;

    @SerializedName("QueryType")
    public final String queryType;

    @SerializedName("ServiceId")
    public final String serviceId;

    @SerializedName("Token")
    public  String token;

    public ServiceRequest(String latitude, String longitude, String type, String queryStr, String queryType, String serviceId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.queryStr = queryStr;
        this.queryType = queryType;
        this.serviceId = serviceId;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
