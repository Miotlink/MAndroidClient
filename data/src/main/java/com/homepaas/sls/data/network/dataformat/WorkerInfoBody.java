package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.entity.WorkerEntity;

/**
 * on 2016/1/12 0012
 *
 * @author zhudongjie .
 */
public class WorkerInfoBody {

    @SerializedName("Worker")
    private WorkerEntity worker;

    public WorkerEntity getWorker() {
        return worker;
    }
}
