package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.FirstOrderInfoNDataSource;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.FirstOrderInfoRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sherily on 2017/2/15.
 */
@Singleton
public class FirstOrderInfoRepoImpl implements FirstOrderInfoRepo {
    @Inject
    public FirstOrderInfoRepoImpl() {
    }
    @Inject
    FirstOrderInfoNDataSource firstOrderInfoNDataSource;
    @Override
    public IsFirstOrderInfo getIsFirstOrderInfo() throws AuthException, GetDataException {
        try {
            return firstOrderInfoNDataSource.getIsFirstOrderInfo();
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }
}
