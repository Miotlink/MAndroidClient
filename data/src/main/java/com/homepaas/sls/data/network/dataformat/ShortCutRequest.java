package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/22.
 */

public class ShortCutRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("Longitude")
    private String longitude;
    @SerializedName("Latitude")
    private String latitude;
    @SerializedName("IsNewVersion")
    private String isNewVersion;

    public ShortCutRequest(String token, String longitude, String latitude,String isNewVersion) {
        this.token = token;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isNewVersion = isNewVersion;
    }

    public ShortCutRequest(String token, String longitude, String latitude) {
        this.token = token;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
