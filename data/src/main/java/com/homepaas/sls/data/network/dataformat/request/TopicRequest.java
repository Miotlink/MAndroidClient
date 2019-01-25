package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sherily on 2017/3/27.
 */

public class TopicRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("Longitude")
    private String longitude;
    @SerializedName("Latitude")
    private String latitude;
    @SerializedName("QueryStr")
    private String queryStr;
    @SerializedName("IsNewVersion")
    private String isNewVersion;

    public TopicRequest(String token, String longitude, String latitude, String queryStr,String isNewVersion) {
        this.token = token;
        this.longitude = longitude;
        this.latitude = latitude;
        this.queryStr = queryStr;
        this.isNewVersion = isNewVersion;
    }
}
