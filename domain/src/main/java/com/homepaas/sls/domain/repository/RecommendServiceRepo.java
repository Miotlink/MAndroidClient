package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.RecommendServiceEntity;

import rx.Observable;

/**
 * Created by Administrator on 2017/3/22.
 */

public interface RecommendServiceRepo {
    Observable<RecommendServiceEntity> getRecommendService(String longitude, String latitude);
}
