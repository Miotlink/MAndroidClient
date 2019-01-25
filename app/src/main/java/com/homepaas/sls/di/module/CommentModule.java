package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.GetMyEvaluationsInteractor;
import com.homepaas.sls.domain.repository.CommentRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/6/20 0020
 *
 * @author zhudongjie
 */
@Module
public class CommentModule {

    @Provides
    @ActivityScope
    GetMyEvaluationsInteractor provideEvaluationsInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, CommentRepo commentRepo){
        return new GetMyEvaluationsInteractor(jobExecutor,postExecutionThread,commentRepo);
    }
}
