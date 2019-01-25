package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.exception.AuthException;

import javax.inject.Singleton;

/**
 * Created by Sherily on 2017/2/15.
 */

public interface FirstOrderInfoNDataSource {
    IsFirstOrderInfo getIsFirstOrderInfo() throws AuthException, GetNetworkDataException, ResponseMetaDataException;
}
