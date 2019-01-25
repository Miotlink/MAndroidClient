package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.RechargeListExEntity;
import com.homepaas.sls.domain.entity.WXPaySignEntry;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/7.
 */

public interface RechargeListExRepo {
    Observable<RechargeListExEntity> getRechargeListEx();
}
