package com.homepaas.sls.domain.entity;

import java.util.List;

/**
 * Created by Sheirly on 2016/9/8.
 */
public class GetWorkerTagsInfo {
    private List<WorkerTagsInfo> workerTagsInfos;
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
