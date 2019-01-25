package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

/**
 * Created by Sherily on 2017/2/15.
 */

public interface FirstOrderInfoRepo {
    IsFirstOrderInfo getIsFirstOrderInfo() throws AuthException, GetDataException;
}
