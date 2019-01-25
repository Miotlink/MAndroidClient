package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.FirstOpenAppInfo;
import com.homepaas.sls.domain.entity.ShareInfo;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface FirstCouponNDataSource {

    FirstOpenAppInfo getFirstCoupon(String token)throws GetNetworkDataException,ResponseMetaDataException,ResponseMetaAuthException;;
}
