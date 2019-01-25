package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.BusinessSercicePriceRequest;
import com.homepaas.sls.data.network.dataformat.request.SearchHotServiceRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.BusinessServicePricesEntity;
import com.homepaas.sls.domain.entity.HotServiceInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.repository.BusinessServicePriceRepo;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/1/9.
 */

public class BusinessServicePriceImpl implements BusinessServicePriceRepo {

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public BusinessServicePriceImpl() {
    }

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Override
    public Observable<BusinessServicePricesEntity> getBusinessServicePriceList( String merchantId, String serviceProviderType, String serviceAddressId) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            BusinessSercicePriceRequest request = new BusinessSercicePriceRequest(token,merchantId, serviceProviderType, serviceAddressId);
            return apiHelper.restApi()
                    .getBusinessServicePrice(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<BusinessServicePricesEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
