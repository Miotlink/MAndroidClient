package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ServiceItemListEntity;

import rx.Observable;

/**
 * Created by Sherily on 2017/3/27.
 */

public interface SearchTopicRepo {
    Observable<ServiceItemListEntity> getQueryService(String longitude, String latitude, String queryStr);
}
