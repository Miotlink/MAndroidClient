package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.repository.datasource.SearchNDataSource;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.NoServiceException;
import com.homepaas.sls.domain.exception.OutOfServiceException;
import com.homepaas.sls.domain.param.SearchParameter;
import com.homepaas.sls.domain.repository.SearchRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by CJJ on 2016/9/8.
 *
 */
@Singleton
public class SearchRepoImpl implements SearchRepo {

    @Inject
    SearchNDataSource searchNDataSource;
    @Inject
    public SearchRepoImpl(){

    }

    @Override
    public ServiceSearchInfo searchService(SearchParameter searchParameter) throws AuthException, GetDataException, OutOfServiceException, NoServiceException {
            return searchNDataSource.searchService(searchParameter);
    }
}
