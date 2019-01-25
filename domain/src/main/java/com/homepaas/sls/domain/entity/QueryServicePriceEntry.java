package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/3/27.
 */

public class QueryServicePriceEntry {
    @SerializedName("Service")
    private ServiceItem serviceItem;

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }
}
