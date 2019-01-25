package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.ShortCutRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.ShortCutEntity;
import com.homepaas.sls.domain.repository.ShortCutRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/3/22.
 */

public class ShortCutRepoImpl implements ShortCutRepo {
    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public ShortCutRepoImpl() {
    }

    @Override
    public Observable<ShortCutEntity> getShortCut(String longitude, String latitude,String isNewVersion) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            return apiHelper.restApi()
                    .getShortCut(new ShortCutRequest(token, longitude, latitude,isNewVersion))
                    .flatMap(new RestApiHelper.ResponseParseFunc<ShortCutEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
