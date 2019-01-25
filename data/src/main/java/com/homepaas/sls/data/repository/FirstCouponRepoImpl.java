package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.FirstCouponNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.FirstOpenAppInfo;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.FirstCouponRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/7/12.
 */
@Singleton
public class FirstCouponRepoImpl implements FirstCouponRepo {

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    FirstCouponNDataSource firstCouponNDataSource;

    @Inject
    public FirstCouponRepoImpl(){}

    @Override
    public FirstOpenAppInfo getFirstConpon() throws AuthException, GetDataException {

        try {
            String token = personalInfoPDataSource.getToken();
            FirstOpenAppInfo firstOpenAppInfo = firstCouponNDataSource.getFirstCoupon(token);
            return firstOpenAppInfo;
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }
}
