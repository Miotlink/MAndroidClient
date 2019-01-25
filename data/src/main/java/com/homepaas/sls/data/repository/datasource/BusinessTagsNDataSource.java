package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;

/**
 * Created by Sheirly on 2016/9/9.
 */
public interface BusinessTagsNDataSource {
    GetBusinessTagsInfo getBusinessTagsInfo(String workerId) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;
}
