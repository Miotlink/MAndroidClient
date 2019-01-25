package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.QueryServicePriceRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.QueryServicePriceEntry;
import com.homepaas.sls.domain.repository.QueryServicePriceRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/3/28.
 */

public class QueryServicePriceImpl implements QueryServicePriceRepo {
    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public QueryServicePriceImpl() {
    }

    @Override
    public Observable<QueryServicePriceEntry> getQueryServicePrice(String serviceId, String longitude, String latitude,String serviceLevel,String providerId,String providerUserType) {
        String token;
        try {
            token=personalInfoPDataSource.getToken();
            QueryServicePriceRequest request=new QueryServicePriceRequest(token,serviceId,longitude,latitude, serviceLevel, providerId, providerUserType);
            return apiHelper.restApi()
                    .getQueryServicePrice(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<QueryServicePriceEntry>());

        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
