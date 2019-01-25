package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.CouponContentsInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

/**
 * Created by Administrator on 2016/6/21.
 */
public interface RedEnvelopeRepo {

    CouponContentsInfo getRedEnvelopeInfo(String status) throws GetDataException, AuthException;
}
