package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

/**
 * Created by Sherily on 2016/10/17.
 */

public interface AddRecommendInfoRepo {
    String addRecommendInfo(String code)throws GetDataException,AuthException;
}
