package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.GetToTakeOrderInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.mvp.view.TokenView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CJJ on 2016/10/14.
 *
 */
@Module
public class TokenModule {


    public TokenModule() {
    }

    @Provides
    @ActivityScope
    Interactor provideTokenInteractor(GetToTakeOrderInteractor interactor)
    {
        return interactor;
    }

}
