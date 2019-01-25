package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sherily on 2017/3/22.
 */

public class QueryServiceRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("Longitude")
    private String longitude;
    @SerializedName("Latitude")
    private String latitude;
    @SerializedName("ServiceId")
    private String serviceId;
    @SerializedName("IsNewVersion")
    private String isNewVersion;

    public QueryServiceRequest(String token, String longitude, String latitude, String serviceId,String isNewVersion) {
        this.token = token;
        this.longitude = longitude;
        this.latitude = latitude;
        this.serviceId = serviceId;
        this.isNewVersion = isNewVersion;
    }
}
