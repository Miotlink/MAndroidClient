package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.QueryServiceRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.ServiceItemListEntity;
import com.homepaas.sls.domain.repository.QueryServiceRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/3/22.
 */

public class QueryServiceRepoImpl implements QueryServiceRepo {
    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public QueryServiceRepoImpl() {
    }

    @Override
    public Observable<ServiceItemListEntity> getQueryService(String longitude, String latitude, String serviceId) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            return apiHelper.restApi()
                    .getQueryService(new QueryServiceRequest(token, longitude, latitude, serviceId,"1"))
                    .flatMap(new RestApiHelper.ResponseParseFunc<ServiceItemListEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
