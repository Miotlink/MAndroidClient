package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.NoServiceException;
import com.homepaas.sls.domain.exception.OutOfServiceException;
import com.homepaas.sls.domain.param.SearchParameter;

/**
 * Created by CJJ on 2016/9/8.
 *
 */
public interface SearchRepo {

    ServiceSearchInfo searchService(SearchParameter searchParameter) throws AuthException, GetDataException, OutOfServiceException, NoServiceException;
}
