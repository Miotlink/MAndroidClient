package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.MerchantServicePriceListEntity;

import rx.Observable;

/**
 * Created by JWC on 2017/2/13.
 */

public interface MerchantServicePriceListRepo {
    Observable<MerchantServicePriceListEntity> getMerchantServicePriceList(String id);
}
