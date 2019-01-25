package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.repository.datasource.WorkerInfoMockDataSource;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.exception.GetDataException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.homepaas.sls.data.repository.datasource.impl.MockData.createWorkerInfo;

/**
 * on 2016/1/19 0019
 *
 * @author zhudongjie .
 */
@Singleton
public class WorkerInfoMockDataSourceImpl implements WorkerInfoMockDataSource {


    @Inject
    public WorkerInfoMockDataSourceImpl() {

    }


    @Override
    public WorkerInfo getWorkerInfo(String workerId) throws GetDataException {
        return createWorkerInfo();
    }

    @Override
    public List<WorkerInfo> getWorkerInfoList() throws GetDataException {
        List<WorkerInfo> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(createWorkerInfo());

        }
        return list;
    }


}
