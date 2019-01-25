package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.interactor.SearchServiceInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CJJ on 2016/9/8.
 */
@Module
public class SearchServiceModule {

    @Provides
    @ActivityScope
    Interactor providerSearchServiceInteractor(SearchServiceInteractor interactor)
    {
        return interactor;
    }
}
