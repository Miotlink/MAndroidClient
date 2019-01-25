package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJJ on 2016/9/14.
 * 参数只需要serviceId的请求
 */

public class ServiceTypeIdRequest {
    @SerializedName("ServiceTypeId")
    String serviceTypeId;

    public ServiceTypeIdRequest() {
    }

    public ServiceTypeIdRequest(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
}
