package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.WorkerCollectionEntity;

import java.io.Serializable;
import java.util.List;

/**
 * on 2016/2/18 0018
 *
 * @author zhudongjie .
 */
public class CollectedWorkerBody implements Serializable{

    @SerializedName("WorkerFavoritesList")
    private List<WorkerCollectionEntity> collectedWorkers;

    public List<WorkerCollectionEntity> getCollectedWorkers() {
        return collectedWorkers;
    }

    public CollectedWorkerBody(List<WorkerCollectionEntity> collectedWorkers) {
        this.collectedWorkers = collectedWorkers;
    }
}
