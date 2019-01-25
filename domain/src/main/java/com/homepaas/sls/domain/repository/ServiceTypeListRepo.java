package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;

import java.io.IOException;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface ServiceTypeListRepo {

    WorkerServiceTypeInfo getWorkerServiceTypeInfo(String id);
    BusinessServiceTypeInfo getBusinessServiceTypeInfo(String id);
}
