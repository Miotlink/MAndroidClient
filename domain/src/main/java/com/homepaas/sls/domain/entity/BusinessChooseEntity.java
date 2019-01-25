package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2017/1/10.
 */

public class BusinessChooseEntity {
    private String serviveName;
    String min;
    String max;
    String unitName;
    String negotiable;
    List<String> priceOptions;
    public String startingPrice;

    public BusinessChooseEntity() {
    }

    public String getServiveName() {
        return serviveName;
    }

    public void setServiveName(String serviveName) {
        this.serviveName = serviveName;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getNegotiable() {
        return negotiable;
    }

    public void setNegotiable(String negotiable) {
        this.negotiable = negotiable;
    }

    public List<String> getPriceOptions() {
        return priceOptions;
    }

    public void setPriceOptions(List<String> priceOptions) {
        this.priceOptions = priceOptions;
    }

    public String getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }
}
