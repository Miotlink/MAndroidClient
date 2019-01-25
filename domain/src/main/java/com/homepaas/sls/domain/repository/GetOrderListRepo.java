package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.OrderInfo;

import rx.Observable;

/**
 * Created by JWC on 2017/7/21.
 */

public interface GetOrderListRepo {
    Observable<OrderInfo> getOrderList(String pageSize, String pageIndex, String type);
}
