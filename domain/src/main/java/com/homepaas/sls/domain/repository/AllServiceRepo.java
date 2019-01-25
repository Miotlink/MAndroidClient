package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ServiceItemListEntity;

import rx.Observable;

/**
 * Created by Administrator on 2017/3/22.
 */

public interface AllServiceRepo {
    Observable<ServiceItemListEntity> getAllService(String longitude, String latitude);
}
