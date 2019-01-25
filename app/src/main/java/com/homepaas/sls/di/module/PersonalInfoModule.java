package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.AddRecommendInfoInteractor;
import com.homepaas.sls.domain.interactor.GetPersonalInfoInteractor;
import com.homepaas.sls.domain.interactor.GetTokenInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.interactor.LogoutInteractor;
import com.homepaas.sls.domain.interactor.ModifyUserNicknameInteractor;
import com.homepaas.sls.domain.interactor.ModifyPhotoInteractor;
import com.homepaas.sls.domain.interactor.ResetPasswordInteractor;
import com.homepaas.sls.domain.repository.AddRecommendInfoRepo;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;
import com.homepaas.sls.mvp.view.personalcenter.PersonalInfoView;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2015/12/24.
 */

@Module
public class PersonalInfoModule {

    PersonalInfoView personalInfoView;

    public PersonalInfoModule() {
    }

    public PersonalInfoModule(PersonalInfoView personalInfoView) {
        this.personalInfoView = personalInfoView;
    }

    @Provides
    @ActivityScope
    GetPersonalInfoInteractor provideGetPersonalInfoInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                               PostExecutionThread postExecutionThread,
                                                               PersonalInfoRepo personalInfoRepo) {
        return new GetPersonalInfoInteractor(jobExecutor, postExecutionThread, personalInfoRepo);
    }

    @Provides
    @ActivityScope
    ModifyUserNicknameInteractor provideModifyNicknameInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                                 PostExecutionThread postExecutionThread,
                                                                 PersonalInfoRepo personalInfoRepo) {
        return new ModifyUserNicknameInteractor(jobExecutor, postExecutionThread,personalInfoRepo);
    }

    @Provides
    @ActivityScope
    ResetPasswordInteractor provideModifyPasswordInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                            PostExecutionThread postExecutionThread,
                                                            PersonalInfoRepo personalInfoRepo) {
        return new ResetPasswordInteractor(jobExecutor, postExecutionThread,personalInfoRepo);
    }


    @Provides
    @ActivityScope
    ModifyPhotoInteractor provideModifyPhotoInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                       PostExecutionThread postExecutionThread,
                                                       PersonalInfoRepo personalInfoRepo){
        return new ModifyPhotoInteractor(jobExecutor,postExecutionThread,personalInfoRepo);
    }

    @Provides
    @ActivityScope
    @Named("LogoutInteractor")
    Interactor provideLogoutInteractor(LogoutInteractor interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    GetTokenInteractor provideGetTokenInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                         PostExecutionThread postExecutionThread,
                                         PersonalInfoRepo personalInfoRepo){
        return new GetTokenInteractor(jobExecutor,postExecutionThread,personalInfoRepo);
    }

    @Provides
    @ActivityScope
    PersonalInfoView providePersonalInfoView(PersonalInfoView view)
    {
        return view;
    }

    @ActivityScope
    @Provides
    AddRecommendInfoInteractor provideAddRecommendInfoInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AddRecommendInfoRepo addRecommendInfoRepo){
        return new AddRecommendInfoInteractor(jobExecutor,postExecutionThread,addRecommendInfoRepo);
    }
}
