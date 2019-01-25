package com.homepaas.sls.data.network.dataformat.request;

/**
 * Created by Administrator on 2016/12/7.
 */

public class GetBusinessServiceListRequest {
    private String Token;
    private String ServiceId;
    private String UserType;
    private String MerchantOrWorkerId;
    private double Latitude;
    private double Longitude;

    public GetBusinessServiceListRequest(String token, String serviceId, String userType, String merchantOrWorkerId, double Latitude, double Longitude) {
        Token = token;
        ServiceId = serviceId;
        UserType = userType;
        MerchantOrWorkerId = merchantOrWorkerId;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getMerchantOrWorkerId() {
        return MerchantOrWorkerId;
    }

    public void setMerchantOrWorkerId(String merchantOrWorkerId) {
        MerchantOrWorkerId = merchantOrWorkerId;
    }
}
