package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.domain.entity.CityListEntity;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.domain.repository.CityRepo;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/7/21.
 */

public class CityRepoImpl implements CityRepo {
    @Inject
    RestApiHelper apiHelper;

    @Inject
    public CityRepoImpl() {
    }

    @Override
    public Observable<CityListEntity> getCityList() {
        return apiHelper.restApi()
                .getCityList()
                .flatMap(new RestApiHelper.ResponseParseFunc<CityListEntity>());
    }
}
