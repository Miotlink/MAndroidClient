package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.entity.WorkerServiceTypeEntity;
import com.homepaas.sls.domain.entity.WorkerServiceType;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 * 工人服务列表
 */
public class WorkerServiceTypeBody {

    @SerializedName("ServiceTypeList")
    List<WorkerServiceTypeEntity> list;

    public WorkerServiceTypeBody() {
    }

    public WorkerServiceTypeBody(List<WorkerServiceTypeEntity> list) {
        this.list = list;
    }

    public List<WorkerServiceTypeEntity> getList() {
        return list;
    }

    public void setList(List<WorkerServiceTypeEntity> list) {
        this.list = list;
    }
}
