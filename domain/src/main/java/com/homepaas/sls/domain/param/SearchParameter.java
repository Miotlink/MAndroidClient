package com.homepaas.sls.domain.param;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/23 0023
 *
 * @author zhudongjie .
 */
public class SearchParameter {

    @SerializedName("Latitude")
    public final String latitude;

    @SerializedName("Longitude")
    public final String longitude;

    @SerializedName("Type")
    public final String serviceType;

    @SerializedName("QueryStr")
    public final String queryContent;

    @SerializedName("QueryType")
    public final String queryType;

    @SerializedName("ServiceId")
    public final String serviceId;

    @SerializedName("Token")
    private String token;

    public SearchParameter(String latitude, String longitude, String serviceType,
                           String queryContent, String queryType, String serviceId) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.serviceType = serviceType;
        this.queryContent = queryContent;
        this.queryType = queryType;
        this.serviceId = serviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
