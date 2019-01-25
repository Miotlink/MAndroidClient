package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.AliPayRechargeInteractor;
import com.homepaas.sls.domain.interactor.GetRechargesInteractor;
import com.homepaas.sls.domain.interactor.WeChatPayRechargeInteractor;
import com.homepaas.sls.domain.repository.AccountInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/6/22 0022
 *
 * @author zhudongjie
 */
@Module
public class RechargeModule {
    @Provides
    @ActivityScope
    GetRechargesInteractor provideGetRechargesInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                         AccountInfoRepo accountInfoRepo){
        return new GetRechargesInteractor(jobExecutor,postExecutionThread,accountInfoRepo);
    }

    @Provides
    @ActivityScope
    AliPayRechargeInteractor provideAliPayRechargeInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                             AccountInfoRepo accountInfoRepo){
        return new AliPayRechargeInteractor(jobExecutor,postExecutionThread,accountInfoRepo);
    }

    @Provides
    @ActivityScope
    WeChatPayRechargeInteractor provideWeChatPayRechargeInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                                   AccountInfoRepo accountInfoRepo){
        return new WeChatPayRechargeInteractor(jobExecutor,postExecutionThread,accountInfoRepo);
    }
}
