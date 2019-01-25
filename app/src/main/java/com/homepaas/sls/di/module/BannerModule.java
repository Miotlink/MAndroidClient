package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.AdsInfoInteractor;
import com.homepaas.sls.domain.interactor.BannersInfoInteractor;
import com.homepaas.sls.domain.repository.AdsRepo;
import com.homepaas.sls.domain.repository.BannersInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sherily on 2016/9/10.
 */
@Module
public class BannerModule {
    @Provides
    @ActivityScope
    BannersInfoInteractor providerBannersInfoInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                        BannersInfoRepo bannersInfoRepo){
        return new BannersInfoInteractor(jobExecutor,postExecutionThread,bannersInfoRepo);
    }

    @Provides
    @ActivityScope
    AdsInfoInteractor providerAdsInfoInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AdsRepo adsRepo){
        return new AdsInfoInteractor(jobExecutor,postExecutionThread,adsRepo);
    }
}
