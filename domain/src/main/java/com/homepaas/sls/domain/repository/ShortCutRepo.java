package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ShortCutEntity;

import rx.Observable;

/**
 * Created by Administrator on 2017/3/22.
 */

public interface ShortCutRepo {
    Observable<ShortCutEntity> getShortCut(String longitude, String latitude,String isNewVersion);
}
