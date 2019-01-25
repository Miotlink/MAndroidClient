package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.GetAccountDetailsInteractor;
import com.homepaas.sls.domain.interactor.GetAccountInfoInteractor;
import com.homepaas.sls.domain.interactor.GetPayDetailsInteractor;
import com.homepaas.sls.domain.repository.AccountInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
@Module
public class AccountModule {

    @Provides
    @ActivityScope
    GetAccountInfoInteractor provideGetAccountInfoInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                             PostExecutionThread postExecutionThread, AccountInfoRepo accountInfoRepo){
        return new GetAccountInfoInteractor(jobExecutor,postExecutionThread,accountInfoRepo);
    }

    @Provides
    @ActivityScope
    GetAccountDetailsInteractor provideGetAccountDetailsInteractor(@Named("concurrent")JobExecutor jobExecutor,
                                                                   PostExecutionThread postExecutionThread,
                                                                   AccountInfoRepo accountInfoRepo){
        return new GetAccountDetailsInteractor(jobExecutor,postExecutionThread,accountInfoRepo);
    }

    @Provides
    @ActivityScope
    GetPayDetailsInteractor provideGetPayDetailsInteractor(@Named("concurrent")JobExecutor jobExecutor,
                                                           PostExecutionThread postExecutionThread,
                                                           AccountInfoRepo accountInfoRepo){
        return new GetPayDetailsInteractor(jobExecutor,postExecutionThread,accountInfoRepo);
    }
}
