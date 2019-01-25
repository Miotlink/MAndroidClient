package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/1/9.
 */

public class BusinessSercicePriceRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("MerchantId")
    private String merchantId;
    @SerializedName("ServiceProviderType")
    private String serviceProviderType;
    @SerializedName("ServiceAddressId")
    private String serviceAddressId;

    public BusinessSercicePriceRequest(String token, String merchantId, String serviceProviderType, String serviceAddressId) {
        this.token = token;
        this.merchantId = merchantId;
        this.serviceProviderType = serviceProviderType;
        this.serviceAddressId = serviceAddressId;
    }

    public BusinessSercicePriceRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getServiceProviderType() {
        return serviceProviderType;
    }

    public void setServiceProviderType(String serviceProviderType) {
        this.serviceProviderType = serviceProviderType;
    }

    public String getServiceAddressId() {
        return serviceAddressId;
    }

    public void setServiceAddressId(String serviceAddressId) {
        this.serviceAddressId = serviceAddressId;
    }
}
