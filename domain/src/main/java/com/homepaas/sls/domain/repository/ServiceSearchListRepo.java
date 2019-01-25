package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ServiceInfo;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.NoServiceException;
import com.homepaas.sls.domain.exception.OutOfServiceException;
import com.homepaas.sls.domain.param.SearchParameter;

/**
 * on 2016/1/13 0013
 *
 * @author zhudongjie .
 */
public interface ServiceSearchListRepo {

    ServiceInfo getServiceInfo(SearchParameter searchParameter) throws GetDataException, OutOfServiceException, NoServiceException;
}
