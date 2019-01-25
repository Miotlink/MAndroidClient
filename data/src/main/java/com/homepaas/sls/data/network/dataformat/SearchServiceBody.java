package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.BusinessEntity;
import com.homepaas.sls.domain.entity.WorkerEntity;

import java.util.List;

/**
 * 主页搜索内容
 *
 * @author zhudongjie .
 */
public class SearchServiceBody {

    @SerializedName("Workers")
    private List<WorkerEntity> workers;

    @SerializedName("Business")
    private List<BusinessEntity> businesses;


    @SerializedName("OtherWorkers")
    private List<WorkerEntity> otherWorkers;

    @SerializedName("OtherBusiness")
    private List<BusinessEntity> otherBusiness;

    public List<BusinessEntity> getBusinesses() {
        return businesses;
    }

    public List<WorkerEntity> getWorkers() {
        return workers;
    }

    public List<WorkerEntity> getOtherWorkers() {
        return otherWorkers;
    }

    public List<BusinessEntity> getOtherBusiness() {
        return otherBusiness;
    }
}
