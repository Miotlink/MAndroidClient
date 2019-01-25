package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.repository.datasource.ServiceSearchListMockDataSource;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.WorkerInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.homepaas.sls.data.repository.datasource.impl.MockData.createBusinessInfo;
import static com.homepaas.sls.data.repository.datasource.impl.MockData.createWorkerInfo;

/**
 * on 2016/1/13 0013
 *
 * @author zhudongjie .
 */
@Singleton
public class ServiceSearchListMockDataSourceImpl implements ServiceSearchListMockDataSource {


    @Inject
    public ServiceSearchListMockDataSourceImpl() {

    }


    @Override
    public List<BusinessInfo> getBusinesses(int length) {
        List<BusinessInfo> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            list.add(createBusinessInfo());
        }
        return list;
    }


    @Override
    public List<WorkerInfo> getWorkers(int length) {
        List<WorkerInfo> workerInfoList = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            WorkerInfo workerInfo = createWorkerInfo();

            workerInfoList.add(workerInfo);

        }
        return workerInfoList;
    }


}
