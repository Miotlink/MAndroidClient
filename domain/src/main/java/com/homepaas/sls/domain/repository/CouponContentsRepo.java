package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.CouponContentsInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

/**
 * Created by Sherily on 2016/6/23.
 */
public interface CouponContentsRepo {
    CouponContentsInfo getCouponContentsInfo(String serviceId, String longitude,String latitude,String ispay) throws GetDataException, AuthException;
}
