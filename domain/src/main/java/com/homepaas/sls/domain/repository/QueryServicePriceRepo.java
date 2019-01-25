package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.QueryServicePriceEntry;

import rx.Observable;

/**
 * Created by JWC on 2017/3/28.
 */

public interface QueryServicePriceRepo {
    Observable<QueryServicePriceEntry> getQueryServicePrice(String serviceId, String longitude, String latitude,String serviceLevel,String providerId,String providerUserType);
}
