package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.AdsInfo;

/**
 * Created by Administrator on 2016/12/27.
 */

public interface AdsInfoNDataSource {
    AdsInfo getAdsInfo()throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;
}
