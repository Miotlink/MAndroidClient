package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.ResetPasswordInteractor;
import com.homepaas.sls.domain.interactor.RequestResetPasswordInteractor;
import com.homepaas.sls.domain.interactor.SendCaptchaInteractor;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/2/4 0004
 *
 * @author zhudongjie .
 */
@Module
public class ResetPasswordModule {

    @Provides
    @ActivityScope
    SendCaptchaInteractor provideSendCaptchaInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                       PersonalInfoRepo personalInfoRepo) {
        return new SendCaptchaInteractor(jobExecutor, postExecutionThread, personalInfoRepo);
    }

    @Provides
    @ActivityScope
    RequestResetPasswordInteractor provideRequestResetPasswordInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                                         PersonalInfoRepo personalInfoRepo) {
        return new RequestResetPasswordInteractor(jobExecutor, postExecutionThread, personalInfoRepo);
    }

    @Provides
    @ActivityScope
    ResetPasswordInteractor provideResetPasswordInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                           PersonalInfoRepo personalInfoRepo) {
        return new ResetPasswordInteractor(jobExecutor, postExecutionThread, personalInfoRepo);
    }
}
