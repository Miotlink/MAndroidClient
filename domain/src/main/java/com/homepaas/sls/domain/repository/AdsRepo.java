package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.AdsInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

/**
 * Created by Sherily on 2016/12/27.
 */

public interface AdsRepo {
    AdsInfo getAdsInfo()throws GetDataException, AuthException;
}
