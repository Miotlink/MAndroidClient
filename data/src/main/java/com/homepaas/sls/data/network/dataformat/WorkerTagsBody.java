package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.WorkerTagsInfo;

import java.util.List;

/**
 * Created by Sherily on 2016/9/8.
 */
public class WorkerTagsBody {
    @SerializedName("TagList")
    private List<WorkerTagsInfo> workerTagsInfos;
    @SerializedName("Count")
    private int count;

    public List<WorkerTagsInfo> getWorkerTagsInfos() {
        return workerTagsInfos;
    }

    public void setWorkerTagsInfos(List<WorkerTagsInfo> workerTagsInfos) {
        this.workerTagsInfos = workerTagsInfos;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
