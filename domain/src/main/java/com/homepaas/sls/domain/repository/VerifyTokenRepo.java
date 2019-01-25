package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.VerifyTokenBody;

import rx.Observable;

/**
 * Created by Sherily on 2017/3/13.
 */

public interface VerifyTokenRepo {
    Observable<VerifyTokenBody> verifyToken();
}
