package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2017/1/9.
 */

public class BusinessServiceListEntity {
    @SerializedName("ServiceTypeList")
    public List<ServiceContent> serviceContents;

    public List<ServiceContent> getServiceContents() {
        return serviceContents;
    }

    public void setServiceContents(List<ServiceContent> serviceContents) {
        this.serviceContents = serviceContents;
    }
}
