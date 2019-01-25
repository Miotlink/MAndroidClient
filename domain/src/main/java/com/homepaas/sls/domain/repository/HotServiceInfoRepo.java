package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.HotServiceInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by JWC on 2016/12/21.
 */

public interface HotServiceInfoRepo {
    Observable<List<HotServiceInfo>> getHotServiceInfoList(String latitude, String longitude);
}
