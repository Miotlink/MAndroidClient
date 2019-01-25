package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJJ on 2016/9/8.
 */
public class ServiceSearchInfo implements Serializable {

    /**
     * 0：表示 Business里没有全城商户，
     * 1：表示Business里有全城商户
     */
    @SerializedName("IsWholeMerchant")
    String hasWholeMerchant;
    @SerializedName("IsOrdering")
    String orderExecutable;//是否是一键下单行为 0：否；1：是
    @SerializedName("PreorderingServiceType")
    String serviceTypeId;//下单的服务id
    @SerializedName("TypeName")
    String serviceName;
    @SerializedName("Workers")
    List<WorkerEntity> workerEntities;
    @SerializedName("WholeWorkers")
    List<WholeCityWorker> wholeCityWorkers;
    @SerializedName("Business")
    List<BusinessEntity> businessEntities;
    @SerializedName("WholeBusiness")
    List<WholeCityBusines> wholeCityBusiness;
    @SerializedName("ServiceTypes")
    List<NewServiceType> serviceTypes;

    public List<WholeCityWorker> getWholeCityWorkers() {
        return wholeCityWorkers;
    }

    public void setWholeCityWorkers(List<WholeCityWorker> wholeCityWorkers) {
        this.wholeCityWorkers = wholeCityWorkers;
    }

    public List<WholeCityBusines> getWholeCityBusiness() {
        return wholeCityBusiness;
    }

    public void setWholeCityBusiness(List<WholeCityBusines> wholeCityBusiness) {
        this.wholeCityBusiness = wholeCityBusiness;
    }

    public List<NewServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<NewServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public String getHasWholeMerchant() {
        return hasWholeMerchant;
    }

    public void setHasWholeMerchant(String hasWholeMerchant) {
        this.hasWholeMerchant = hasWholeMerchant;
    }

    private List<IService> wrapperList;
    private List<IService> searchServiceList;

    public List<WorkerEntity> getWorkerEntities() {
        return workerEntities;
    }

    public void setWorkerEntities(List<WorkerEntity> workerEntities) {
        this.workerEntities = workerEntities;
    }

    public List<BusinessEntity> getBusinessEntities() {
        return businessEntities;
    }

    public void setBusinessEntities(List<BusinessEntity> businessEntities) {
        this.businessEntities = businessEntities;
    }

    public List<IService> getWrapperList() {
        if (wrapperList == null) {
            wrapperList = new ArrayList<>();
        } else {
            wrapperList.clear();
        }
        if (wholeCityWorkers != null) {
            wrapperList.addAll(wholeCityWorkers);
        }
        if (wholeCityBusiness != null) {
            wrapperList.addAll(wholeCityBusiness);
        }
        if (workerEntities != null)
            wrapperList.addAll(workerEntities);
        if (businessEntities != null)
            wrapperList.addAll(businessEntities);
        return wrapperList;
    }

    public List<IService> getSearchServiceList() {
        if (searchServiceList == null) {
            searchServiceList = new ArrayList<>();
        } else {
            searchServiceList.clear();
        }
        if (serviceTypes != null)
            searchServiceList.addAll(serviceTypes);
        if (wholeCityWorkers != null) {
            searchServiceList.addAll(wholeCityWorkers);
        }
        if (workerEntities != null)
            searchServiceList.addAll(workerEntities);
        if (wholeCityBusiness != null) {
            searchServiceList.addAll(wholeCityBusiness);
        }
        if (businessEntities != null)
            searchServiceList.addAll(businessEntities);
        return searchServiceList;
    }

    public int getWholeServiceNumber() {
        int wholeCityWorkerInt = 0;
        int wholeCityBusinessInt = 0;
        if (wholeCityWorkers != null) {
            wholeCityWorkerInt = wholeCityWorkers.size();
        }
        if (wholeCityBusiness != null) {
            wholeCityBusinessInt = wholeCityBusiness.size();
        }
        return wholeCityWorkerInt + wholeCityBusinessInt;
    }

    public String getOrderExecutable() {
        return orderExecutable;
    }

    public void setOrderExecutable(String orderExecutable) {
        this.orderExecutable = orderExecutable;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    //for model
    public boolean isOrdering() {
        return "1".equals(orderExecutable);
    }
}
