package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.AddRecommendInfoDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.AddRecommendInfoRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sherily on 2016/10/17.
 */
@Singleton
public class AddRecommendInfoRepoImpl implements AddRecommendInfoRepo {

    @Inject
    public AddRecommendInfoRepoImpl() {
    }

    @Inject
    AddRecommendInfoDataSource addRecommendInfoDataSource;

    @Inject
    PersonalInfoPDataSource pDataSource;

    @Override
    public String addRecommendInfo(String code) throws GetDataException, AuthException {
        try {
            String token = pDataSource.getToken();
            return addRecommendInfoDataSource.addRecommendInfo(token,code);
        } catch (GetPersistenceDataException e) {
            throw new AuthException(e.getMessage(),e);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(),e);
        }
    }
}
