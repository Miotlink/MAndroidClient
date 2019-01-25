package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJJ on 2016/7/13.
 */
public class GetDescriptionRequest {
    @SerializedName("Code")
    String code;
    @SerializedName("ServiceId")
    String serviceId;//服务类型id
    @SerializedName("IsAndroid")
    String platForm;

    public GetDescriptionRequest() {
    }

    public GetDescriptionRequest(String code, String serviceId) {
        this.code = code;
        this.serviceId = serviceId;
        this.platForm = "1";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
