package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.MerchantServicePriceListRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.BusinessServicePricesEntity;
import com.homepaas.sls.domain.entity.MerchantServicePriceListEntity;
import com.homepaas.sls.domain.repository.MerchantServicePriceListRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/2/13.
 */

public class MerchantServicePriceListImpl implements MerchantServicePriceListRepo {

    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public MerchantServicePriceListImpl() {
    }

    @Override
    public Observable<MerchantServicePriceListEntity> getMerchantServicePriceList(String id) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            MerchantServicePriceListRequest merchantServicePriceListRequest = new MerchantServicePriceListRequest(token, id);
            return apiHelper.restApi()
                    .getMerchantServicePriceList(merchantServicePriceListRequest)
                    .flatMap(new RestApiHelper.ResponseParseFunc<MerchantServicePriceListEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
