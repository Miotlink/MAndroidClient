package com.homepaas.sls.data.entity;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.ServiceTypeEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/4/28.
 */
public class WorkerServiceTypeEntity {

    @SerializedName("ServiceTypeId")
    private String servicetypeid;
    @SerializedName("ServiceTypeName")
    private String servicetypename;
    @SerializedName("Price")
    private String Price;
    @SerializedName("PriceType")
    private String PriceType;
    @SerializedName("Unit")
    private String Unit;
    @SerializedName("UnitName")
    private String UnitName;
    @SerializedName("ChildrenService")
    private List<ServiceTypeEntity> ChildrenService;

    public WorkerServiceTypeEntity() {
    }

    public String getServiceTypeId() {
        return servicetypeid;
    }

    public void setServiceTypeId(String ServiceTypeId) {
        this.servicetypeid = ServiceTypeId;
    }

    public String getServiceTypeName() {
        return servicetypename;
    }

    public void setServiceTypeName(String ServiceTypeName) {
        this.servicetypename = ServiceTypeName;
    }

    public String getServicetypeid() {
        return servicetypeid;
    }

    public void setServicetypeid(String servicetypeid) {
        this.servicetypeid = servicetypeid;
    }

    public String getServicetypename() {
        return servicetypename;
    }

    public void setServicetypename(String servicetypename) {
        this.servicetypename = servicetypename;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPriceType() {
        return PriceType;
    }

    public void setPriceType(String priceType) {
        PriceType = priceType;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public List<ServiceTypeEntity> getChildrenService() {
        return ChildrenService;
    }

    public void setChildrenService(List<ServiceTypeEntity> childrenService) {
        ChildrenService = childrenService;
    }
}
