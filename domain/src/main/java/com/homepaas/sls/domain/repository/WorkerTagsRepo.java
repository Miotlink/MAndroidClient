package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

import java.util.PriorityQueue;

/**
 * Created by Sherily on 2016/9/8.
 */
public interface WorkerTagsRepo {
    GetWorkerTagsInfo getWorkerTagsInfo(String workerId) throws GetDataException, AuthException;
}
