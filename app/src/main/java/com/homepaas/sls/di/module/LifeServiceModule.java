package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.GetHotServiceListInteractor;
import com.homepaas.sls.domain.interactor.GetLifeServiceListInteractor;
import com.homepaas.sls.domain.interactor.Interactor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/1/22.
 */

@Module
public class LifeServiceModule {

    @Provides
    @ActivityScope
    @Named("GetLifeServiceListInteractor")
    Interactor provideGetLifeServiceListInteractor(GetLifeServiceListInteractor interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("GetHotServiceListInteractor")
    Interactor provideGetHotServiceListInteractor(GetHotServiceListInteractor interactor) {
        return interactor;
    }
}
