package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.NoSearchServiceException;
import com.homepaas.sls.data.exception.OutOfSearchServiceException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.repository.datasource.PersonalInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.SearchNDataSource;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.NoServiceException;
import com.homepaas.sls.domain.exception.OutOfServiceException;
import com.homepaas.sls.domain.param.SearchParameter;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by CJJ on 2016/9/8.
 *
 */
@Singleton
public class SearchNDataSourceImpl implements SearchNDataSource {

    @Inject
    public SearchNDataSourceImpl() {
    }

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    RestApiHelper apiHelper;
    @Override
    public ServiceSearchInfo searchService(SearchParameter searchParameter) throws AuthException, GetDataException, OutOfServiceException, NoServiceException {

        try {
            searchParameter.setToken(personalInfoPDataSource.getToken());
            Response<ResponseWrapper<ServiceSearchInfo>> response = apiHelper.searchServices(searchParameter);
            Meta meta = response.body().meta;
            if (meta.isValid()) {
                return response.body().data;
            } else if (Meta.CODE_NO_SERVICE.equals(meta.getErrorCode())) {
                throw new NoServiceException(meta.getErrorMsg());
            } else if (Meta.CODE_OUT_OF_SERVICE.equals(meta.getErrorCode())) {
                throw new OutOfServiceException(meta.getErrorMsg());
            } else if (meta.isAuthFailed()) {
                throw new AuthException(meta.getErrorMsg());
            } else {
                throw new GetDataException(response.body().meta.getErrorMsg());
            }
        } catch (IOException e) {
            throw new GetDataException(e);
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e);
        } catch (GetPersistenceDataException e) {
            throw new AuthException(e);
        }
    }
}
