package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.GetCouponContentsRequest;
import com.homepaas.sls.data.network.dataformat.request.QueryServicePriceRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.CommonCouponInfo;
import com.homepaas.sls.domain.repository.CommonCouponInfoRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/5/26.
 */

public class CommonCouponInfoImpl implements CommonCouponInfoRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public CommonCouponInfoImpl() {
    }

    @Override
    public Observable<CommonCouponInfo> getCommonCouponInfo(String serviceId, String longitude, String latitude, String isPay) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            GetCouponContentsRequest request = new GetCouponContentsRequest(token, serviceId, longitude, latitude, isPay);
            return apiHelper.restApi()
                    .getCommonCouponInfo(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<CommonCouponInfo>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
