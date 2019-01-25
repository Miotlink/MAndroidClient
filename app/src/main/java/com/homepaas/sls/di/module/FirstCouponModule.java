package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.GetFirstOpenAppInfoInteractor;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.PopuverInteractor;
import com.homepaas.sls.domain.repository.FirstCouponRepo;
import com.homepaas.sls.domain.repository.PopuverRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/7/12.
 */
@Module
public class FirstCouponModule {

    @Provides
    @ActivityScope
    GetFirstOpenAppInfoInteractor providerGetFirstCouponInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, FirstCouponRepo firstCouponRepo){
        return new GetFirstOpenAppInfoInteractor(jobExecutor,postExecutionThread,firstCouponRepo);
    }

    @Provides
    @ActivityScope
    PopuverInteractor providerPopuverInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, PopuverRepo popuverRepo){
        return new PopuverInteractor(jobExecutor,postExecutionThread,popuverRepo);
    }

}
