package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.AdsInfoNDataSource;
import com.homepaas.sls.domain.entity.AdsInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.AdsRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/12/27.
 */
@Singleton
public class AdsRepoImpl implements AdsRepo {
    @Inject
    AdsInfoNDataSource adsInfoNDataSource;

    @Inject
    public AdsRepoImpl() {
    }

    @Override
    public AdsInfo getAdsInfo() throws GetDataException, AuthException {
        try {
            return adsInfoNDataSource.getAdsInfo();
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }
}
