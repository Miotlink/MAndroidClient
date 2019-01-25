package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.ShortCutRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.ServiceItemListEntity;
import com.homepaas.sls.domain.repository.HotSearchServiceRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/3/27.
 */

public class HotSearchServiceRepoImpl implements HotSearchServiceRepo {
    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public HotSearchServiceRepoImpl() {
    }

    @Override
    public Observable<ServiceItemListEntity> getQueryService(String longitude, String latitude) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            return apiHelper.restApi()
                    .hotSearchService(new ShortCutRequest(token, longitude,latitude,"1"))
                    .flatMap(new RestApiHelper.ResponseParseFunc<ServiceItemListEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
