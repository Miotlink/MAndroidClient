package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.RechargeInfoEntry;
import com.homepaas.sls.domain.entity.RechargeInfoResponse;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/7.
 */

public interface RechargeInfoRepo {
    Observable<RechargeInfoResponse> getRechargeInfo(String rechargeCode);
}
