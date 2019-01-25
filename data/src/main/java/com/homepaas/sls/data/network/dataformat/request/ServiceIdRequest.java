package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJJ on 2016/9/23.
 */

public class ServiceIdRequest {

    @SerializedName("ServiceId")
    public String serviceTypeId;

    public ServiceIdRequest() {
    }

    public ServiceIdRequest(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
}
