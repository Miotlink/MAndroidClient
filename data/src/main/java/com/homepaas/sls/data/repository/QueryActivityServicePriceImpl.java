package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.QueryActivityServicePriceRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.QueryServicePriceEntry;
import com.homepaas.sls.domain.repository.QueryActivityServicePriceRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/6/2.
 */

public class QueryActivityServicePriceImpl implements QueryActivityServicePriceRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    public QueryActivityServicePriceImpl() {
    }

    @Override
    public Observable<QueryServicePriceEntry> getQueryActivityServicePrice(String activityProductId, String longitude, String latitude) {
        String token;
        try {
            token=personalInfoPDataSource.getToken();
            QueryActivityServicePriceRequest request=new QueryActivityServicePriceRequest(token,activityProductId,longitude,latitude);
            return apiHelper.restApi()
                    .getQueryActivityServicePrice(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<QueryServicePriceEntry>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
