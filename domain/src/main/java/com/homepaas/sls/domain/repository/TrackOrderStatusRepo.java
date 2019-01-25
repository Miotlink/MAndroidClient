package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.TrackOrderInfo;

import rx.Observable;

/**
 * Created by JWC on 2017/7/19.
 */

public interface TrackOrderStatusRepo {
    Observable<TrackOrderInfo> getTrackOrderStatus(String orderId);
}
