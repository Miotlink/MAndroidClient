package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.GetDescriptInteractor;
import com.homepaas.sls.domain.interactor.Interactor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CJJ on 2016/7/13.
 */
@Module
public class DescriptionModule {

    @Provides
    @ActivityScope
    @Named("GetDescriptInteractor")
    Interactor provideGetDescriptionInteractor(GetDescriptInteractor interactor) {
        return interactor;
    }
}
