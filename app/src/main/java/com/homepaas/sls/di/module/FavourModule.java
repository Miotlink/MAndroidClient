package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.GetMyRedPacketInteractor;
import com.homepaas.sls.domain.interactor.Interactor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/4/28.
 * 优惠活动模块，包括红包，红包活动等内容
 */
@Module
public class FavourModule {

    @Provides
    @ActivityScope
    @Named("GetMyRedPacketInteractor")
    Interactor provideRedPacketInteractor(GetMyRedPacketInteractor interactor){
        return interactor;
    }
}
