package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sherily on 2017/3/22.
 */

public class RecommendItem implements Serializable{
    @SerializedName("Title")
    private String title;
    @SerializedName("Price")
    private String price;
    @SerializedName("Unit")
    private String unit;
    @SerializedName("LogoUrl")
    private List<String> logoUrl;
    @SerializedName("Service")
    private ServiceItem serviceItem;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<String> getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(List<String> logoUrl) {
        this.logoUrl = logoUrl;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }
}
