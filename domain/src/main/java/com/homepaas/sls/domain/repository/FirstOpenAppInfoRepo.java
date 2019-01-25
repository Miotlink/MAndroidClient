package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.NewFirstOpenAppInfo;

import rx.Observable;

/**
 * Created by JWC on 2017/8/14.
 */

public interface FirstOpenAppInfoRepo {
    Observable<NewFirstOpenAppInfo> getFirstOpenAppInfo();
}
