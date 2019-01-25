package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

/**
 * Created by Sherily on 2016/9/8.
 */
public interface BusinessTagsRepo {
    GetBusinessTagsInfo getBusinessTagsInfo(String workerId) throws GetDataException, AuthException;
}
