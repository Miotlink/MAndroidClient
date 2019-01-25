package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ExpressDetailOutputEntity;

import rx.Observable;

/**
 * Created by JWC on 2017/7/6.
 */

public interface GetErrandServiceDetailRepo {
    Observable<ExpressDetailOutputEntity> getErrandServiceDetail(String orderId);
}
