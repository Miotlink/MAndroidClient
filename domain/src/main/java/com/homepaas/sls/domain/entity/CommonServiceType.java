package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2017/1/10.
 */

public class CommonServiceType implements Serializable{
    public String ServiceTypeId;
    public String ServiceTypeName;
    public String Price;
    public String PriceType;
    public String Unit;
    public String UnitName;
    public String StartingPrice;
    public List<CommonServiceType> ChildrenService;

    public int tag;//for Adapter to identify the item

    public String getServiceTypeId() {
        return ServiceTypeId;
    }

    public void setServiceTypeId(String ServiceTypeId) {
        this.ServiceTypeId = ServiceTypeId;
    }

    public String getServiceTypeName() {
        return ServiceTypeName;
    }

    public void setServiceTypeName(String ServiceTypeName) {
        this.ServiceTypeName = ServiceTypeName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getPriceType() {
        return PriceType;
    }

    public void setPriceType(String PriceType) {
        this.PriceType = PriceType;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String UnitName) {
        this.UnitName = UnitName;
    }

    public List<CommonServiceType> getChildrenService() {
        return ChildrenService;
    }

    public void setChildrenService(List<CommonServiceType> childrenService) {
        ChildrenService = childrenService;
    }

    public String getStartingPrice() {
        return StartingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        StartingPrice = startingPrice;
    }
}
