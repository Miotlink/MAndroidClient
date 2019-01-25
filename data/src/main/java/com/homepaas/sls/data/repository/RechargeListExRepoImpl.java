package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.domain.entity.RechargeListExEntity;
import com.homepaas.sls.domain.entity.WXPaySignEntry;
import com.homepaas.sls.domain.repository.RechargeListExRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/7.
 */

public class RechargeListExRepoImpl implements RechargeListExRepo {

    @Inject
    public RechargeListExRepoImpl() {
    }

    @Inject
    RestApiHelper restApiHelper;


    @Override
    public Observable<RechargeListExEntity> getRechargeListEx() {
        return restApiHelper.restApi()
                .getRechargeListEx()
                .flatMap(new RestApiHelper.ResponseParseFunc<RechargeListExEntity>());
    }
}
