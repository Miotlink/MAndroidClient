package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.OrderCancelReasonEntity;


import rx.Observable;

/**
 * Created by JWC on 2017/6/1.
 */

public interface OrderCancelReasonRepo {
    Observable<OrderCancelReasonEntity> getOrderCancelReasonList();
}
