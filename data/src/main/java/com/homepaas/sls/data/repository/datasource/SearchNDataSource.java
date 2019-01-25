package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.NoServiceException;
import com.homepaas.sls.domain.exception.OutOfServiceException;
import com.homepaas.sls.domain.param.SearchParameter;

/**
 * Created by CJJ on 2016/9/8.
 */
public interface SearchNDataSource {
    ServiceSearchInfo searchService(SearchParameter searchParameter) throws AuthException, GetDataException, OutOfServiceException, NoServiceException;
}
