package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.WorkerCollectionEntity;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.AuthException;

import java.util.List;

/**
 * on 2016/1/19 0019
 *
 * @author zhudongjie .
 */
public interface WorkerInfoRepo {

    WorkerInfo getWorkerInfo(String workerId) throws GetDataException;

    List<WorkerCollectionEntity> getCollectedWorkerList() throws GetDataException, AuthException;

    boolean likeWorker(String workerId, boolean like) throws GetDataException,AuthException;

    boolean collectWorker(String workerId, boolean collect) throws GetDataException,AuthException;

    boolean checkCallable(String workerId,String phone) throws GetDataException;

    List<Evaluation> getEvaluationList(String workerId, int pageIndex, int pageSize)throws GetDataException;

    String reportWorker(String workerId)throws GetDataException;
}
