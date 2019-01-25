package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.OrderCancelReasonEntity;
import com.homepaas.sls.domain.repository.OrderCancelReasonRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/6/1.
 */

public class OrderCancelReasonImpl implements OrderCancelReasonRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public OrderCancelReasonImpl() {
    }

    @Override
    public Observable<OrderCancelReasonEntity> getOrderCancelReasonList() {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            TokenRequest request = new TokenRequest(token);
            return apiHelper.restApi()
                    .getOrderCancelReasonList(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<OrderCancelReasonEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
