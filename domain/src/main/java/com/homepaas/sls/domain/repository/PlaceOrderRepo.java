package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.PlaceOrderEntry;
import com.homepaas.sls.domain.param.PlaceOrderParam;

import rx.Observable;

/**
 * Created by JWC on 2017/3/29.
 */

public interface PlaceOrderRepo {
    Observable<PlaceOrderEntry> getPlaceOrdre(PlaceOrderParam placeOrderParam);
}
