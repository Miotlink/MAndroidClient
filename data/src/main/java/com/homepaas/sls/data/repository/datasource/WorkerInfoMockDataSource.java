package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.exception.GetDataException;

import java.util.List;

/**
 * on 2016/1/19 0019
 *
 * @author zhudongjie .
 */
public interface WorkerInfoMockDataSource {
    WorkerInfo getWorkerInfo(String workerId) throws GetDataException;
    List<WorkerInfo> getWorkerInfoList()throws GetDataException;
}
