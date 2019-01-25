package com.homepaas.sls.domain.param;

import java.util.List;

/**
 * Created by Administrator on 2016/4/28.
 */
public class OrderCreateParams {

    List<String> filePaths;
    String objectType;//工作对象类型：2 工人 3 商户
    String objectId;//工人商户id
    String serviceTypeId;//工人商户服务类型id
    String serviceContent;
    String servicePrice;
    String orderForm;
    String serviceNumber;
    String addressId;
    private String serviceTime;

    public OrderCreateParams() {
    }

    public OrderCreateParams(List<String> filePaths, String objectType, String objectId, String serviceTypeId, String serviceContent,String serviceNumber, String orderForm, String serviceTime,String addressId) {
        this.filePaths = filePaths;
        this.objectType = objectType;
        this.objectId = objectId;
        this.serviceTypeId = serviceTypeId;
        this.serviceContent = serviceContent;
        this.serviceTime = serviceTime;
        this.orderForm = orderForm;
        this.addressId = addressId;
        this.serviceNumber = serviceNumber;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
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

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }
}
