package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.BusinessServiceListRequest;
import com.homepaas.sls.domain.entity.BusinessServiceListEntity;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.repository.BusinessServiceListRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/1/9.
 */

public class BusinessServiceListImpl implements BusinessServiceListRepo {
    @Inject
    RestApiHelper apiHelper;

    @Inject
    public BusinessServiceListImpl() {
    }

    @Override
    public Observable<BusinessServiceTypeInfo> getBusinessServiceList(String id) {
        BusinessServiceListRequest request=new BusinessServiceListRequest(id);
        return apiHelper.restApi()
                .getBusinessServiceList(request)
                .flatMap(new RestApiHelper.ResponseParseFunc<BusinessServiceTypeInfo>());
    }
}
