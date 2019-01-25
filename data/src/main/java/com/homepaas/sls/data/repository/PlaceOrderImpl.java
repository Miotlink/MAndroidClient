package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.PlaceOrderRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.PlaceOrderEntry;
import com.homepaas.sls.domain.param.PlaceOrderParam;
import com.homepaas.sls.domain.repository.PlaceOrderRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/3/29.
 */

public class PlaceOrderImpl implements PlaceOrderRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public PlaceOrderImpl() {
    }

    @Override
    public Observable<PlaceOrderEntry> getPlaceOrdre(PlaceOrderParam placeOrderParam) {
        String token;
        try {
            token=personalInfoPDataSource.getToken();
            placeOrderParam.setToken(token);
            PlaceOrderRequest request=new PlaceOrderRequest(placeOrderParam);
            return apiHelper.restApi()
                    .getPlaceOrder(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<PlaceOrderEntry>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
