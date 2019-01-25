package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.BannersInfoNDataSource;
import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.BannersInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.BannersInfoRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sherily on 2016/9/10.
 */
@Singleton
public class BannersInfoRepoImpl implements BannersInfoRepo {
    @Inject
    BannersInfoNDataSource bannersInfoNDataSource;

    @Inject
    public BannersInfoRepoImpl() {
    }

    @Override
    public List<BannerInfo> getBannersInfo() throws GetDataException, AuthException {
        try {
            return bannersInfoNDataSource.getBannersInfo();
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }
}
