package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.StarLevelInfo;

import rx.Observable;

/**
 * Created by JWC on 2017/7/21.
 */

public interface GetStarLevelRepo {
    Observable<StarLevelInfo> GetStarLevel(String serviceTypeId);
}
