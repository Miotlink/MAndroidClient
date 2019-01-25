package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.param.Constant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by Administrator on 2016/4/27.
 */
public class WorkerServiceTypeInfo {

    @SerializedName("ServiceTypeList")
    List<WorkerServiceType> list;

    public WorkerServiceTypeInfo() {
    }

    public List<WorkerServiceType> getList() {
        return list;
    }

    public void setList(List<WorkerServiceType> list) {
        this.list = list;
    }

    public List<String> transform() {
        List<String> results = new ArrayList<>();
        for (WorkerServiceType item : list
                ) {
            results.add(item.getServiceTypeName());
        }
        return results;
    }

    public List<WorkerServiceType> flat() {
        List<WorkerServiceType> results = new ArrayList<>();
        if (list != null) {
            for (WorkerServiceType item : list) {
                WorkerServiceType service = new WorkerServiceType();
                service.setPrice(item.Price);
                service.setServiceTypeName(item.ServiceTypeName);
                service.setPriceType(item.PriceType);
                service.setUnit(item.Unit);
                service.setUnitName(item.UnitName);
                service.setServiceTypeId(item.ServiceTypeId);

                if (item.ChildrenService != null) {

                    for (WorkerServiceType childItem : item.ChildrenService) {
                        WorkerServiceType service1 = new WorkerServiceType();
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


    public List<CommonServiceType> getWorkerCommonList(){
        List<CommonServiceType> results = new ArrayList<>();
        if (list != null) {
            for (WorkerServiceType item : list) {
                CommonServiceType service = new CommonServiceType();
                service.setPrice(item.Price);
                service.setServiceTypeName(item.ServiceTypeName);
                service.setPriceType(item.PriceType);
                service.setUnit(item.Unit);
                service.setUnitName(item.UnitName);
                service.setServiceTypeId(item.ServiceTypeId);

                if (item.ChildrenService != null&&!item.ChildrenService.isEmpty()) {
                    for (WorkerServiceType childItem : item.ChildrenService) {
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
                }else{
                    results.add(service);
                }
            }
        }
        return results;
    }


}
