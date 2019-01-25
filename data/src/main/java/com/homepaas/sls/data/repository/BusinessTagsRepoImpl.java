package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.BusinessTagsNDataSource;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.BusinessTagsRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sheirly on 2016/9/9.
 */
@Singleton
public class BusinessTagsRepoImpl implements BusinessTagsRepo{
    @Inject
    BusinessTagsNDataSource businessTagsNDataSource;

    @Inject
    public BusinessTagsRepoImpl() {
    }

    @Override
    public GetBusinessTagsInfo getBusinessTagsInfo(String workerId) throws GetDataException, AuthException {
        try {
            return businessTagsNDataSource.getBusinessTagsInfo(workerId);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }
}
