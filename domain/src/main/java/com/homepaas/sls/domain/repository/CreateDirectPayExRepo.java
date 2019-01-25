package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.CreateDirectPayExEntity;
import com.homepaas.sls.domain.entity.WorkerServicePriceListEntity;

import rx.Observable;

/**
 * Created by JWC on 2017/2/17.
 */

public interface CreateDirectPayExRepo {
    Observable<CreateDirectPayExEntity> getCreateDirectPayEx(String receiverID, String receiverType, String paySource, String totalMoney, String activityNgId);
}
