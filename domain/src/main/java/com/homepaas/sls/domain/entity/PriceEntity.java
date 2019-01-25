package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.param.Constant;

import java.util.List;

/**
 * Created by CJJ on 2016/9/14.
 *
 */

public class PriceEntity {

    @SerializedName("Min")
    String min;
    @SerializedName("Max")
    String max;
    @SerializedName("UnitName")
    String unitName;
    @SerializedName("IsNegotiable")
    String negotiable;
    @SerializedName("PriceList")
    List<String> priceOptions;
    @SerializedName("StartingPrice")
    public String startingPrice;

    @SerializedName("ServiceTypeId")
    private String serviceTypeId;

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    String negEnum;


    public String getNegEnum() {
        return negEnum;
    }

    public void setNegEnum(String negEnum) {
        this.negEnum = negEnum;
//        negotiable = Constant.NEGOTIATE.equals(negEnum);
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
