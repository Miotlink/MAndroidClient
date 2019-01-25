package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.AutoLoginInteractor;
import com.homepaas.sls.domain.interactor.CheckLoginStateInteractor;
import com.homepaas.sls.domain.interactor.GetLoginInfoInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.interactor.LoginInteractor;
import com.homepaas.sls.domain.interactor.QuickLoginInteractor;
import com.homepaas.sls.domain.interactor.SendCaptchaInteractor;
import com.homepaas.sls.domain.repository.LoginInfoRepo;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/2/14 0014
 *
 * @author zhudongjie .
 */
@Module
public class LoginModule {

    @Provides
    @ActivityScope
    SendCaptchaInteractor provideSendCaptchaInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                       PersonalInfoRepo personalInfoRepo) {
        return new SendCaptchaInteractor(jobExecutor, postExecutionThread, personalInfoRepo);
    }

    @Provides
    @ActivityScope
    LoginInteractor provideLoginInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                           PersonalInfoRepo personalInfoRepo) {
        return new LoginInteractor(jobExecutor, postExecutionThread, personalInfoRepo);
    }

    @Provides
    @ActivityScope
    QuickLoginInteractor provideQuickLoginInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                     PersonalInfoRepo personalInfoRepo){
        return new QuickLoginInteractor(jobExecutor,postExecutionThread,personalInfoRepo);
    }
    @Provides
    @ActivityScope
    @Named("AutoLoginInteractor")
    Interactor provideAutoLoginInteractor(AutoLoginInteractor interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("CheckLoginStateInteractor")
    Interactor provideCheckLoginStateInteractor(CheckLoginStateInteractor interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    GetLoginInfoInteractor provideGetAccountInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                       LoginInfoRepo loginInfoRepo){
        return new GetLoginInfoInteractor(jobExecutor,postExecutionThread, loginInfoRepo);
    }
}
