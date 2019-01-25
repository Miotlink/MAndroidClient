package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.RegisterInteractor;
import com.homepaas.sls.domain.interactor.SendCaptchaInteractor;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/2/1 0001
 *
 * @author zhudongjie .
 */
@Module
public class RegisterModule {

    @Provides
    @ActivityScope
    SendCaptchaInteractor provideSendCaptchaInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                       PersonalInfoRepo personalInfoRepo) {
        return new SendCaptchaInteractor(jobExecutor, postExecutionThread, personalInfoRepo);
    }

    @Provides
    @ActivityScope
    RegisterInteractor provideRegisterInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                 PersonalInfoRepo personalInfoRepo) {
        return new RegisterInteractor(jobExecutor, postExecutionThread, personalInfoRepo);
    }
}
