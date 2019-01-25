package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2017/2/13.
 */

public class WorkerServicePriceListEntity {
    @SerializedName("ServiceTypeList")
    private List<WorkerServiceTypePrice> totalList;

    public List<WorkerServiceTypePrice> getTotalList() {
        return totalList;
    }

    public void setTotalList(List<WorkerServiceTypePrice> totalList) {
        this.totalList = totalList;
    }


}
