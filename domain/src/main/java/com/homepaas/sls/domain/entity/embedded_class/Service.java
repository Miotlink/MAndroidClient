package com.homepaas.sls.domain.entity.embedded_class;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CJJ on 2016/9/11.
 *
 */

public class Service {

    @SerializedName("ServiceId")
    public String serviceId;
    @SerializedName("ServiceName")
    public String serviceName;
    @SerializedName("Content")
    public List<String> content;
    @SerializedName("Pictures")
    public List<Picture> pictures;
    @SerializedName("Price")
    public String price;
    @SerializedName("TotalPrice")
    String totalPrice;
    @SerializedName("Total")
    String total;
    @SerializedName("UnitName")
    String unitName;
    @SerializedName("ServiceStartTime")
    String serviceStartAt;
    @SerializedName("AddressInfo")
    Address addressInfo;
    //服务类型的root, 家政,安装等
    @SerializedName("RootId")
    String rootId;
    //支付界面的服务图片
    @SerializedName("ServiceIcon")
    String serviceIcon;
    //订单列表的服务图片
    @SerializedName("ServiceProductIcon")
    String serviceProductIcon;

    public String getServiceProductIcon() {
        return serviceProductIcon;
    }

    public void setServiceProductIcon(String serviceProductIcon) {
        this.serviceProductIcon = serviceProductIcon;
    }

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
    }

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

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getServiceStartAt() {
        return serviceStartAt;
    }

    public void setServiceStartAt(String serviceStartAt) {
        this.serviceStartAt = serviceStartAt;
    }

    public Address getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(Address addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getAddress() {
        return addressInfo.getAddress1() + addressInfo.getAddress2();
    }

    public String getRootId() {
        return rootId;
    }
}
