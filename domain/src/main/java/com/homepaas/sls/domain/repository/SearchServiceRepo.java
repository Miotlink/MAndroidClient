package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ServiceSearchInfo;

import rx.Observable;

/**
 * Created by JWC on 2016/12/20.
 */

public interface SearchServiceRepo {
    Observable<ServiceSearchInfo> getSearchService(String latitude, String longitude, String type, String queryStr, String queryType, String serviceId);
}
