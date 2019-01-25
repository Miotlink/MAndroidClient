package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.ExpressDetailRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.ExpressDetailOutputEntity;
import com.homepaas.sls.domain.repository.GetErrandServiceDetailRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/7/6.
 */

public class GetErrandServiceDetailImpl implements GetErrandServiceDetailRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    public GetErrandServiceDetailImpl() {
    }

    @Override
    public Observable<ExpressDetailOutputEntity> getErrandServiceDetail(String orderId) {
        String token;
        try {
            token=personalInfoPDataSource.getToken();
            ExpressDetailRequest request=new ExpressDetailRequest(token,orderId);
            return apiHelper.restApi()
                    .getErrandServiceDetail(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<ExpressDetailOutputEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
