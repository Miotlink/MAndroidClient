package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/6/20 0020
 *
 * @author zhudongjie
 */
public class ServiceContent {

    @SerializedName("Id")
    private String id;

    @SerializedName("ServiceName")
    private String serviceName;

    @SerializedName("ServiceTypeId")
    private String serviceTypeId;

    @SerializedName("ServiceTypeName")
    private String serviceTypeName;

    @SerializedName("Description")
    private String description;

    @SerializedName("Price")
    private String price;

    @SerializedName("Details")
    private String detail;

    @SerializedName("PicPath")
    private String picturePath;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
