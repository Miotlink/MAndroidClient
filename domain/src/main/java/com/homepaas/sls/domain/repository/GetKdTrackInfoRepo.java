package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.KdTrackInfoResponse;

import rx.Observable;

/**
 * Created by JWC on 2017/6/5.
 */

public interface GetKdTrackInfoRepo {
    Observable<KdTrackInfoResponse> getKdTrackInfo(String logisticCode, String shipperCode);
}
