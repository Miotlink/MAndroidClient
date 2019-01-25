package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.GetFirstOrderInfoInteractor;
import com.homepaas.sls.domain.repository.FirstOrderInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sherily on 2017/2/15.
 */
@Module
public class FirstOrderInfoModule {
    @Provides
    @ActivityScope
    GetFirstOrderInfoInteractor provideGetFirstOrderInfoInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                                   FirstOrderInfoRepo firstOrderInfoRepo){
        return new GetFirstOrderInfoInteractor(jobExecutor,postExecutionThread,firstOrderInfoRepo);
    }
}
