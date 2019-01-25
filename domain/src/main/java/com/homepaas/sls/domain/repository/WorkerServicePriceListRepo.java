package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.WorkerServicePriceListEntity;

import rx.Observable;

/**
 * Created by JWC on 2017/2/13.
 */

public interface WorkerServicePriceListRepo {
    Observable<WorkerServicePriceListEntity> getWorkerServicePriceList(String id);
}
