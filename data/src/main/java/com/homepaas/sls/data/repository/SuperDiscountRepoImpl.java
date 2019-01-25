package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.ShortCutRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.ShortCutEntity;
import com.homepaas.sls.domain.entity.SuperDiscountEntity;
import com.homepaas.sls.domain.repository.SuperDiscountRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/7/20.
 */

public class SuperDiscountRepoImpl implements SuperDiscountRepo {
    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public SuperDiscountRepoImpl() {
    }

    @Override
    public Observable<SuperDiscountEntity> getSuperDiscount(String longitude, String latitude) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            return apiHelper.restApi()
                    .getSuperDiscount(new ShortCutRequest(token, longitude, latitude))
                    .flatMap(new RestApiHelper.ResponseParseFunc<SuperDiscountEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
