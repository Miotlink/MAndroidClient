package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.AvatarsInteractor;
import com.homepaas.sls.domain.interactor.GetActivityInteractor;
import com.homepaas.sls.domain.interactor.GetServicePriceInteractor;
import com.homepaas.sls.domain.interactor.GetServiceScheduleInteractor;
import com.homepaas.sls.domain.interactor.GetSystemConfigInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.repository.AvatarRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CJJ on 2016/9/14.
 *
 */
@Module
public class ServiceModule {

    @Provides
    @ActivityScope
    Interactor provideGetServiceScheduleInteractor(GetServiceScheduleInteractor interactor)
    {
        return  interactor;
    }

    @Provides
    @ActivityScope
    Interactor provideGetServicePriceInteractor(GetServicePriceInteractor interactor)
    {
        return interactor;
    }

    @ActivityScope
    @Provides
    Interactor provideActivityInteractor(GetActivityInteractor interactor){
        return interactor;
    }

    @ActivityScope
    @Provides
    AvatarsInteractor providerGetAvatarInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AvatarRepo avatarRepo){
        return new AvatarsInteractor(jobExecutor,postExecutionThread,avatarRepo);
    }

    @ActivityScope
    @Provides
    Interactor provideGetSystemConfigInteractor(GetSystemConfigInteractor interactor){
        return interactor;
    }
}
