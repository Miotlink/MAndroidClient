package com.homepaas.sls.domain.param;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CJJ on 2016/9/19.
 */

public class DirectOrderParam {

    @SerializedName("Token")
    String token;
    @SerializedName("")
    List<String> photoPaths;
    @SerializedName("ServiceTypeId")
    String serviceTypeId;
    @SerializedName("Total")
    String serviceNumber;
    @SerializedName("ServiceContent")
    String serviceContent;
    @SerializedName("OrderFrom")
    String orderSource;
    @SerializedName("ServiceStartAt")
    String serviceTime;
    @SerializedName("ServiceAddressId")
    String serviceAddressId;
    @SerializedName("ServicePrice")
    private String servicePrice;


    public DirectOrderParam() {
        orderSource = Constant.ANDROID_TYPE;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getServiceAddressId() {
        return serviceAddressId;
    }

    public void setServiceAddressId(String serviceAddressId) {
        this.serviceAddressId = serviceAddressId;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServicePrice() {
        return servicePrice;
    }
}
