package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2016/12/21.
 */

public class HotServiceInfo {
    @SerializedName("ServiceId")
    private String serviceId;
    @SerializedName("Name")
    private String name;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
