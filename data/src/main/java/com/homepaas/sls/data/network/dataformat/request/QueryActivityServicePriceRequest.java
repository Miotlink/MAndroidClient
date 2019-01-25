package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/6/2.
 */

public class QueryActivityServicePriceRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("ActivityProductId") //必填；参与活动产品的Id。
    private String activityProductId;
    @SerializedName("Longitude") //经度
    private String longitude;
    @SerializedName("Latitude") //纬度
    private String latitude;

    public QueryActivityServicePriceRequest(String token, String activityProductId, String longitude, String latitude) {
        this.token = token;
        this.activityProductId = activityProductId;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
