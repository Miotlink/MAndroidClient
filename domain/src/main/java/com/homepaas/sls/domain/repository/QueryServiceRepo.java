package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ServiceItemListEntity;

import rx.Observable;

/**
 * Created by Sherily on 2017/3/22.
 */

public interface QueryServiceRepo {
    Observable<ServiceItemListEntity> getQueryService(String longitude, String latitude, String serviceId);
}
