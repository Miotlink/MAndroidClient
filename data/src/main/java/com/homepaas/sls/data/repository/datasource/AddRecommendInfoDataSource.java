package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.exception.AuthException;

/**
 * Created by Sherily on 2016/10/17.
 */

public interface AddRecommendInfoDataSource {

    String addRecommendInfo(String token,String code) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException, AuthException;
}
