package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/3/27.
 * 3.3.0下单获取四级类目
 */

public class QueryServicePriceRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("ServiceId")
    private String serviceId;
    @SerializedName("Longitude") //经度
    private String longitude;
    @SerializedName("Latitude") //纬度
    private String latitude;
//    ServiceLevel，2：二级，3：三级；如果为空，则默认为二级
//    ProviderId，工人或者商户的Id
//    ProviderUserId，类型，2：工人，3：商户
    @SerializedName("ServiceLevel")
    private String serviceLevel;
    @SerializedName("ProviderUserId")
    private String providerUserId;
    @SerializedName("ProviderUserType")
    private String providerUserType;

    public QueryServicePriceRequest(String token, String serviceId, String longitude, String latitude) {
        this.token = token;
        this.serviceId = serviceId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public QueryServicePriceRequest(String token, String serviceId, String longitude, String latitude, String serviceLevel, String providerId, String providerUserId) {
        this.token = token;
        this.serviceId = serviceId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.serviceLevel = serviceLevel;
        this.providerUserId = providerId;
        this.providerUserType = providerUserId;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getProviderUserType() {
        return providerUserType;
    }

    public void setProviderUserType(String providerUserType) {
        this.providerUserType = providerUserType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
