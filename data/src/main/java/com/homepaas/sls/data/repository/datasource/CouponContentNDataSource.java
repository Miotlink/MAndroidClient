package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.CouponContentsInfo;

import java.io.IOException;

/**
 * Created by Sheirly on 2016/6/23.
 */
public interface CouponContentNDataSource {
    CouponContentsInfo getCouponList(String token,String serviceId, String longitude,String latitude,String ispay) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException, GetPersistenceDataException, IOException;
}
