package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.QueryServicePriceEntry;

import rx.Observable;

/**
 * Created by JWC on 2017/6/2.
 */

public interface QueryActivityServicePriceRepo {
    Observable<QueryServicePriceEntry> getQueryActivityServicePrice(String activityProductId, String longitude, String latitude);
}
