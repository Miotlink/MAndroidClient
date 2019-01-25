package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.domain.repository.ServiceTypeExRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Date: 2016/9/13.
 * Author: fmisser
 * Describe:
 */

@Singleton
public class ServiceTypeExRepositoryImpl implements ServiceTypeExRepository {

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public ServiceTypeExRepositoryImpl() {

    }

    @Override
    public Observable<List<ServiceTypeEx>> getInfoListEx() {
        return apiHelper.restApi()
                .getInfoListEx()
                .flatMap(new RestApiHelper.ResponseParseFunc<List<ServiceTypeEx>>());
    }
}
