package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.GetCouponContentsInteractor;
import com.homepaas.sls.domain.repository.CouponContentsRepo;
import com.homepaas.sls.domain.repository.RedEnvelopeRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/6/23.
 */
@Module
public class CouponContentsModule {

    @Provides
    @ActivityScope
    GetCouponContentsInteractor providerGetCouponContentsInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, CouponContentsRepo couponContentsRepo) {
        return new GetCouponContentsInteractor(jobExecutor,postExecutionThread,couponContentsRepo);
    }

}
