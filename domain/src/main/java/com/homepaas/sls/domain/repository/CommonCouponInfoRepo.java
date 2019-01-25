package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.CommonCouponInfo;

import rx.Observable;

/**
 * Created by JWC on 2017/5/26.
 */

public interface CommonCouponInfoRepo {
    Observable<CommonCouponInfo> getCommonCouponInfo(String serviceId, String longitude, String latitude, String isPay);
}
