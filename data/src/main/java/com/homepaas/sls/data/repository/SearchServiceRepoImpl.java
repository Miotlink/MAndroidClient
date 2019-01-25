package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.RechargeInfoRequest;
import com.homepaas.sls.data.network.dataformat.request.SearchServiceRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.RechargeInfoResponse;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.repository.SearchServiceRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2016/12/20.
 */

public  class SearchServiceRepoImpl implements SearchServiceRepo {

    @Inject
    public SearchServiceRepoImpl() {
    }
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    RestApiHelper restApiHelper;


    @Override
    public Observable<ServiceSearchInfo> getSearchService(String latitude, String longitude, String type, String queryStr, String queryType, String serviceId) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            SearchServiceRequest request = new SearchServiceRequest(latitude,longitude,type,queryStr,queryType,serviceId,token);
            return restApiHelper.restApi()
                    .getSearchService(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<ServiceSearchInfo>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
