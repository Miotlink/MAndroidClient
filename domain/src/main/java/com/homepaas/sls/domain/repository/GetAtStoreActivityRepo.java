package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.GetAtStoreActivityEntity;

import rx.Observable;

/**
 * Created by JWC on 2017/2/22.
 */

public interface GetAtStoreActivityRepo {
    Observable<GetAtStoreActivityEntity> getGetAtStoreActivity(String merchantId);
}
