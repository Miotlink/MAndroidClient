package com.homepaas.sls.domain.entity;

import java.util.List;

/**
 * on 2016/1/15 0015
 *
 * @author zhudongjie .
 */
public class ServiceInfo {

    private List<WorkerInfo> workers;

    private List<WorkerInfo> otherWorkers;

    private List<BusinessInfo> businesses;

    private List<BusinessInfo> otherBusinesses;

    public List<WorkerInfo> getWorkers() {
        return workers;
    }

    public void setWorkers(List<WorkerInfo> workers) {
        this.workers = workers;
    }

    public List<WorkerInfo> getOtherWorkers() {
        return otherWorkers;
    }

    public void setOtherWorkers(List<WorkerInfo> otherWorkers) {
        this.otherWorkers = otherWorkers;
    }

    public List<BusinessInfo> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<BusinessInfo> businesses) {
        this.businesses = businesses;
    }

    public List<BusinessInfo> getOtherBusinesses() {
        return otherBusinesses;
    }

    public void setOtherBusinesses(List<BusinessInfo> otherBusinesses) {
        this.otherBusinesses = otherBusinesses;
    }
}
