package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.WorkerTagsInfo;

/**
 * Created by Sheirly on 2016/9/8.
 */
public interface WorkerTagsNDataSource {
    GetWorkerTagsInfo getWorkerTagsInfo(String workerId) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;
}
