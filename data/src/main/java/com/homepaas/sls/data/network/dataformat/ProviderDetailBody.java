package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.entity.BusinessEntity;
import com.homepaas.sls.data.entity.WorkerEntity;

/**
 * Created by Sherily on 2016/9/16.
 */
public class ProviderDetailBody {
    @SerializedName("Worker")
    private WorkerEntity workerEntity;
    @SerializedName("Business")
    private BusinessEntity businessEntity;

    public WorkerEntity getWorkerEntity() {
        return workerEntity;
    }

    public void setWorkerEntity(WorkerEntity workerEntity) {
        this.workerEntity = workerEntity;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }
}
