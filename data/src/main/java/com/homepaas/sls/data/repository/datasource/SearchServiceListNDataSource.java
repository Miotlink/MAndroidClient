package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.NoSearchServiceException;
import com.homepaas.sls.data.exception.OutOfSearchServiceException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.ServiceInfo;
import com.homepaas.sls.domain.param.SearchParameter;

/**
 * on 2016/1/29 0029
 *
 * @author zhudongjie .
 */
public interface SearchServiceListNDataSource {
    ServiceInfo getService(SearchParameter searchParameter, String token) throws GetNetworkDataException, ResponseMetaDataException, NoSearchServiceException, OutOfSearchServiceException;
}
