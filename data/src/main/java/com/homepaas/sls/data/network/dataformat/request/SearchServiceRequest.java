package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2016/12/20.
 */

public class SearchServiceRequest {
    @SerializedName("Latitude")
    private String latitude;
    @SerializedName("Longitude")
    private String longitude;
    @SerializedName("Type")
    private String type;
    @SerializedName("QueryStr")
    private String queryStr;
    @SerializedName("QueryType")
    private String queryType;
    @SerializedName("ServiceId")
    private String serviceId;
    @SerializedName("Token")
    private String token;

    public SearchServiceRequest() {
    }

    public SearchServiceRequest(String latitude, String longitude, String type, String queryStr, String queryType, String serviceId, String token) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.queryStr = queryStr;
        this.queryType = queryType;
        this.serviceId = serviceId;
        this.token = token;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
