package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ActivityNgInfoNew;

import rx.Observable;

/**
 * Created by JWC on 2017/4/1.
 */

public interface GetActivityExRepo {
    Observable<ActivityNgInfoNew> getGetActivityEx(String serviceId);
}
