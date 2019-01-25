package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;

import java.io.IOException;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface ServiceTypeListNDataSource {

    WorkerServiceTypeInfo getWorkerServiceTypeInfo(String id) throws IOException, ResponseMetaAuthException;
    BusinessServiceTypeInfo getBusinessServiceTypeInfo(String id) throws IOException;
}
