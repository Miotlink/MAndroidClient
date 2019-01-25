package com.homepaas.sls.domain.repository;

import rx.Observable;

/**
 * Created by JWC on 2017/7/6.
 */

public interface SubmitApplyClaimsRepo {
    Observable<String> submitApplyClaims(String orderId, String reasonType, String workerLaterTime, String reason);
}
