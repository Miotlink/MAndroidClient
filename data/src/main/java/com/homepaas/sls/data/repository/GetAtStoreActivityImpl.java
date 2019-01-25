package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.GetAtStoreActivityRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;

import com.homepaas.sls.domain.entity.GetAtStoreActivityEntity;
import com.homepaas.sls.domain.repository.GetAtStoreActivityRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/2/22.
 */

public class GetAtStoreActivityImpl implements GetAtStoreActivityRepo {

    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public GetAtStoreActivityImpl() {
    }


    @Override
    public Observable<GetAtStoreActivityEntity> getGetAtStoreActivity(String merchantId) {

        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            GetAtStoreActivityRequest getAtStoreActivityRequest = new GetAtStoreActivityRequest(token, merchantId);
            return apiHelper.restApi()
                    .getGetAtStoreActivity(getAtStoreActivityRequest)
                    .flatMap(new RestApiHelper.ResponseParseFunc<GetAtStoreActivityEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }

        return null;
    }
}
