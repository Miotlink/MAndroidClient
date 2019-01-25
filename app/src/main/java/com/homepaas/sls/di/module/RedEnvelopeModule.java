package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.GetRedEnvelopeInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.repository.RedEnvelopeRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/6/22.
 */
@Module
public class RedEnvelopeModule {

    @Provides
    @ActivityScope
    GetRedEnvelopeInteractor provideGetRedEnvelopeInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, RedEnvelopeRepo redEnvelopeRepo) {
        return new GetRedEnvelopeInteractor(jobExecutor,postExecutionThread,redEnvelopeRepo);
    }
}
