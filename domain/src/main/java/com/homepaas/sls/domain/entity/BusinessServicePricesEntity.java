package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2017/1/10.
 */

public class BusinessServicePricesEntity {
    @SerializedName("ServicePrices")
    List<ServicePriceRangeResponse> servicePrices;

    public List<ServicePriceRangeResponse> getServicePrices() {
        return servicePrices;
    }

    public void setServicePrices(List<ServicePriceRangeResponse> servicePrices) {
        this.servicePrices = servicePrices;
    }
}
