package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
public class BusinessServiceTypeInfo implements Serializable{

    @SerializedName("ServiceTypeList")
    List<BusinessServiceType> list;

    public BusinessServiceTypeInfo() {
    }

    public BusinessServiceTypeInfo(List<BusinessServiceType> list) {
        this.list = list;
    }

    public List<BusinessServiceType> getList() {
        return list;
    }

    public void setList(List<BusinessServiceType> list) {
        this.list = list;
    }

    public List<String> transform() {
        List<String> results = new ArrayList<>();
        for (BusinessServiceType item:list
                ) {
            results.add(item.getServiceTypeName());
        }
        return results;
    }

    public List<CommonServiceType> getbusinessCommonList(){
        List<CommonServiceType> results = new ArrayList<>();
        if (list != null) {
            for (BusinessServiceType item : list) {
                CommonServiceType service = new CommonServiceType();
                service.setPrice(item.Price);
                service.setServiceTypeName(item.ServiceTypeName);
                service.setPriceType(item.PriceType);
                service.setUnit(item.Unit);
                service.setUnitName(item.UnitName);
                service.setServiceTypeId(item.ServiceTypeId);

                if (item.ChildrenService != null) {

                    for (BusinessServiceType childItem : item.ChildrenService) {
                        CommonServiceType service1 = new CommonServiceType();
                        service1.setPrice(childItem.Price);
                        service1.setServiceTypeName(childItem.ServiceTypeName);
                        service1.setPriceType(childItem.PriceType);
                        service1.setUnit(childItem.Unit);
                        service1.setUnitName(childItem.UnitName);
                        service1.setServiceTypeId(childItem.ServiceTypeId);
                        service1.setServiceTypeName(item.getServiceTypeName() + "(" + childItem.getServiceTypeName() + ")");
                        results.add(service1);
                    }

                }else results.add(service);
            }
        }
        return results;
    }
}
