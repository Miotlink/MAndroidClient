package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.WorkerInfo;

import java.util.List;

/**
 * on 2016/1/13 0013
 *
 * @author zhudongjie .
 */
public interface ServiceSearchListMockDataSource {

    List<BusinessInfo> getBusinesses(int length);

    List<WorkerInfo> getWorkers(int length);
}
