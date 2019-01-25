package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */

public class RecommendBlock implements Serializable {

    @SerializedName("ProductCount")
    private int productCount;
    @SerializedName("Title")
    private String title;
    @SerializedName("SubTitle")
    private String subTitle;
    @SerializedName("ServiceId")
    private String serviceId;
    @SerializedName("Items")
    private List<ServiceItem> recommendItems;

    public String getTitle() {
        return title;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public List<ServiceItem> getRecommendItems() {
        return recommendItems;
    }

    public void setRecommendItems(List<ServiceItem> recommendItems) {
        this.recommendItems = recommendItems;
    }
}
