package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.GetOrderRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.OrderInfo;
import com.homepaas.sls.domain.repository.GetOrderListRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/7/21.
 */

public class GetOrderListImpl implements GetOrderListRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public GetOrderListImpl() {
    }

    @Override
    public Observable<OrderInfo> getOrderList(String pageSize, String pageIndex, String type) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            GetOrderRequest request = new GetOrderRequest(token, pageSize, pageIndex, type);
            return apiHelper.restApi()
                    .getOrderList(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<OrderInfo>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
