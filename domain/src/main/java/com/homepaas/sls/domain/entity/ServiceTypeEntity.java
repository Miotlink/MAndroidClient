package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CJJ on 2016/9/24.
 * 服务类型，工人或者商户提交的服务列表中使用
 */

public class ServiceTypeEntity {

    private String ServiceTypeId;
    private String ServiceTypeName;
    private String Price;
    private String PriceType;
    private String Unit;
    private String UnitName;
    private List<ServiceTypeEntity> ChildrenService;

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

    public List<ServiceTypeEntity> getChildrenService() {
        return ChildrenService;
    }

    public void setChildrenService(List<ServiceTypeEntity> ChildrenService) {
        this.ChildrenService = ChildrenService;
    }
}
