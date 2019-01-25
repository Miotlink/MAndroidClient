package com.homepaas.sls.jsinterface.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/30.
 */

public class PricingCommand {
    @SerializedName("Meta")
    private Meta meta;
    @SerializedName("Body")
    private Data data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Meta{
        @SerializedName("ErrorCode")
        private String errorCode;
        @SerializedName("ErrorMsg")
        private String errorMsg;

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }

    public static class Data{
        @SerializedName("ServiceId")
        private String serviceId;
        @SerializedName("ServiceName")
        private String serviceName;

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }
    }

}
