package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.WorkerCollectionEntity;
import com.homepaas.sls.domain.entity.WorkerInfo;

import java.util.List;

/**
 * on 2016/1/29 0029
 *
 * @author zhudongjie .
 */
public interface WorkerInfoNDataSource {

    WorkerInfo getWorkerInfo(String workerId,String token) throws GetNetworkDataException,  ResponseMetaDataException;

    boolean likeWorker(String workerId,String token, boolean like) throws GetNetworkDataException,  ResponseMetaAuthException, ResponseMetaDataException;

    boolean collectWorker(String workerId,String token ,boolean collect) throws GetNetworkDataException,  ResponseMetaAuthException, ResponseMetaDataException;

    List<WorkerCollectionEntity> getCollectedWorkerList(String token) throws GetNetworkDataException,  ResponseMetaAuthException, ResponseMetaDataException;

    boolean checkCallable(String worker,String phone,String deviceId) throws GetNetworkDataException, ResponseMetaDataException;

    List<Evaluation> getEvaluationList(String id, int pageIndex, int pageSize)throws GetNetworkDataException,ResponseMetaDataException;

    String reportWorker(String id,String token)throws GetNetworkDataException,ResponseMetaDataException;
}
