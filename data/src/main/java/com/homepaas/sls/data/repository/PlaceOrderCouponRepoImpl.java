package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/5/26.
 */

public class PlaceOrderCouponRepoImpl{
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    public PlaceOrderCouponRepoImpl() {
    }

}
