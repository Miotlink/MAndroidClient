package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.ShortCutRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.RecommendServiceEntity;
import com.homepaas.sls.domain.repository.RecommendServiceRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/3/22.
 */

public class RecommendServiceRepoImpl implements RecommendServiceRepo {
    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public RecommendServiceRepoImpl() {
    }

    @Override
    public Observable<RecommendServiceEntity> getRecommendService(String longitude, String latitude) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            return apiHelper.restApi()
                    .getRecommendService(new ShortCutRequest(token, longitude, latitude,"1"))
                    .flatMap(new RestApiHelper.ResponseParseFunc<RecommendServiceEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
