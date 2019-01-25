package com.homepaas.sls.data.entity.mapper;

import com.homepaas.sls.data.network.dataformat.WorkerTagsBody;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.entity.WorkerTagsInfo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/9/9.
 */
@Singleton
public class WorkerTagsInfoMapper {
    @Inject
    public WorkerTagsInfoMapper() {
    }

    public GetWorkerTagsInfo transform(WorkerTagsBody workerTagsBody){
        GetWorkerTagsInfo getWorkerTagsInfo = new GetWorkerTagsInfo();
        getWorkerTagsInfo.setCount(workerTagsBody.getCount());
        getWorkerTagsInfo.setWorkerTagsInfos(workerTagsBody.getWorkerTagsInfos());
        return getWorkerTagsInfo;
    }
}
