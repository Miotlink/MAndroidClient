package com.homepaas.sls.di.module;

import com.homepaas.sls.domain.interactor.GetAccountBalanceInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.mvp.view.pay.GetBalanceView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CMJ on 2016/6/25.
 */
@Module
public class PayModule {


    GetBalanceView getBalanceView;

    public PayModule() {
    }

    public PayModule(GetBalanceView getBalanceView) {
        this.getBalanceView = getBalanceView;
    }

    @Provides
    @Named("GetAccountBalanceInteractor")
    public Interactor provideGetBalanceInfoInteractor(GetAccountBalanceInteractor interactor){
        return interactor;
    }

    /*********************provide view*********************************/
    @Provides
    public GetBalanceView  provideGetBalanceView(){
        return getBalanceView;
    }

}
