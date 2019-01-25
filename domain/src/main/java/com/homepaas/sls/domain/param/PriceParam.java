package com.homepaas.sls.domain.param;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJJ on 2016/9/14.
 */

public class PriceParam {

    @SerializedName("Token")
    public String token;
    @SerializedName("ServiceTypeId")
    public String serviceTypeId;
    @SerializedName("ServiceProviderType")
    public String providerType;
    @SerializedName("ServiceAddressId")
    public String serviceAddressId;


    public PriceParam() {
    }

    public PriceParam(String serviceTypeId, String providerType,String serviceAddressId) {
        this.serviceTypeId = serviceTypeId;
        this.providerType = providerType;
        this.serviceAddressId=serviceAddressId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServiceAddressId() {
        return serviceAddressId;
    }

    public void setServiceAddressId(String serviceAddressId) {
        this.serviceAddressId = serviceAddressId;
    }
}
