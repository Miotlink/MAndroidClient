package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.SuperDiscountEntity;

import rx.Observable;

/**
 * Created by Administrator on 2017/7/20.
 */

public interface SuperDiscountRepo {
    Observable<SuperDiscountEntity> getSuperDiscount(String longitude, String latitude);
}
