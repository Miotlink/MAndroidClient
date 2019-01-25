package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.InvalidPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.domain.entity.WorkerInfo;

import java.util.List;

/**
 * on 2016/2/23 0023
 *
 * @author zhudongjie .
 */
public interface WorkerInfoPDataSource {

    WorkerInfo get(String workerId)throws GetPersistenceDataException, InvalidPersistenceDataException;

    List<WorkerInfo> getCollectedWorkers()throws GetPersistenceDataException, InvalidPersistenceDataException;

    List<WorkerInfo> getSearchedWorkers()throws GetPersistenceDataException, InvalidPersistenceDataException;

    void save(List<WorkerInfo> workers,int dataFrom) throws PersistDataException;

    void save(WorkerInfo worker,int dataFrom) throws PersistDataException;

}
