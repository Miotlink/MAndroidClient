package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/4/28.
 */
public class CreateOrderRequest {

    JSONDATA jsonParams;

    List<String> filePath;


    public CreateOrderRequest() {
    }

    public CreateOrderRequest(JSONDATA jsonParams, List<String> filePath) {
        this.jsonParams = jsonParams;
        this.filePath = filePath;
    }

    public JSONDATA getJsonParams() {
        return jsonParams;
    }

    public List<String> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
    }

    public void setJsonParams(JSONDATA jsonParams) {
        this.jsonParams = jsonParams;
    }

    public static class JSONDATA {

        @SerializedName("Token")
        String token;
        @SerializedName("ObjectType")
        String objectType;
        @SerializedName("ObjectId")
        String objectId;
        @SerializedName("ServiceTypeId")
        String serviceTypeId;
        @SerializedName("ServiceContent")
        String serviceContent;
        @SerializedName("ServicePrice")
        String servicePrice;
        @SerializedName("OrderFrom")
        String orderForm;
        @SerializedName("Total")
        String serviceNumber;
        @SerializedName("ServiceStartAt")
        String serviceTime;
        @SerializedName("ServiceAddressId")
        String addressId;

        public JSONDATA() {
        }

        public JSONDATA(String token, String objectType, String objectId, String serviceTypeId, String serviceContent, String servicePrice, String orderForm) {
            this.token = token;
            this.objectType = objectType;
            this.objectId = objectId;
            this.serviceTypeId = serviceTypeId;
            this.serviceContent = serviceContent;
            this.servicePrice = servicePrice;
            this.orderForm = orderForm;
        }

        public JSONDATA(String token, String objectType, String objectId, String serviceTypeId, String serviceContent, String orderForm, String serviceNumber, String serviceTime, String addressId) {
            this.token = token;
            this.objectType = objectType;
            this.objectId = objectId;
            this.serviceTypeId = serviceTypeId;
            this.serviceContent = serviceContent;
            this.orderForm = orderForm;
            this.serviceNumber = serviceNumber;
            this.serviceTime = serviceTime;
            this.addressId = addressId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getObjectType() {
            return objectType;
        }

        public void setObjectType(String objectType) {
            this.objectType = objectType;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getServiceTypeId() {
            return serviceTypeId;
        }

        public void setServiceTypeId(String serviceTypeId) {
            this.serviceTypeId = serviceTypeId;
        }

        public String getServiceContent() {
            return serviceContent;
        }

        public void setServiceContent(String serviceContent) {
            this.serviceContent = serviceContent;
        }

        public String getServicePrice() {
            return servicePrice;
        }

        public void setServicePrice(String servicePrice) {
            this.servicePrice = servicePrice;
        }

        public String getOrderForm() {
            return orderForm;
        }

        public void setOrderForm(String orderForm) {
            this.orderForm = orderForm;
        }
    }
}
