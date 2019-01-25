package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.FeedbackInteractor;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/1/23 0023
 *
 * @author zhudongjie .
 */
@Module
public class FeedbackModule {

    @Provides
    @ActivityScope
    FeedbackInteractor provideFeedbackInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, PersonalInfoRepo personalInfoRepo) {
        return new FeedbackInteractor(jobExecutor, postExecutionThread,personalInfoRepo);
    }
}
