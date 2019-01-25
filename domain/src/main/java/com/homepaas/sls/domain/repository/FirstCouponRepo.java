package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.FirstOpenAppInfo;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface FirstCouponRepo {

    FirstOpenAppInfo getFirstConpon() throws AuthException, GetDataException;
}
