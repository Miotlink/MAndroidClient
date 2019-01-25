package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.CityListEntity;

import rx.Observable;

/**
 * Created by Administrator on 2017/7/21.
 */

public interface CityRepo {
    Observable<CityListEntity> getCityList();
}
