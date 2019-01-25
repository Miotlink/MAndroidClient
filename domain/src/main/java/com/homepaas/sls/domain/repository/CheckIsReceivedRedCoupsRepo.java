package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.CheckIsReceivedRedCoupsEntry;

import rx.Observable;

/**
 * Created by JWC on 2017/3/8.
 */

public interface CheckIsReceivedRedCoupsRepo {
    Observable<CheckIsReceivedRedCoupsEntry> getCheckIsReceivedRedCoups();
}
