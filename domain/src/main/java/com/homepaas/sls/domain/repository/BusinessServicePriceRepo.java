package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.BusinessServicePricesEntity;
import com.homepaas.sls.domain.entity.PriceEntity;

import rx.Observable;

/**
 * Created by JWC on 2017/1/9.
 */

public interface BusinessServicePriceRepo {
    Observable<BusinessServicePricesEntity> getBusinessServicePriceList(String merchantId, String serviceProviderType, String serviceAddressId);
}
