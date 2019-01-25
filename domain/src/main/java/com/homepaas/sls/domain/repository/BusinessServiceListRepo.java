package com.homepaas.sls.domain.repository;


import com.homepaas.sls.domain.entity.BusinessServiceListEntity;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;

import rx.Observable;

/**
 * Created by JWC on 2017/1/9.
 */

public interface BusinessServiceListRepo {
    Observable<BusinessServiceTypeInfo> getBusinessServiceList(String id);
}
