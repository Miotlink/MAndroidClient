package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.AddShareRecordInteractor;
import com.homepaas.sls.domain.interactor.AvatarsInteractor;
import com.homepaas.sls.domain.interactor.GetShareInfoInteractor;
import com.homepaas.sls.domain.repository.AvatarRepo;
import com.homepaas.sls.domain.repository.ShareRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/7/12.
 */
@Module
public class ShareModule {

    @Provides
    @ActivityScope
    GetShareInfoInteractor provideGetShareInfoInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                         ShareRepo shareRepo){
        return new GetShareInfoInteractor(jobExecutor,postExecutionThread,shareRepo);
    }

    @Provides
    @ActivityScope
    AddShareRecordInteractor provideAddShareInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                       ShareRepo shareRepo){
        return new AddShareRecordInteractor(jobExecutor,postExecutionThread,shareRepo);
    }
    @Provides
    @ActivityScope
    AvatarsInteractor providerAvatarsInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AvatarRepo avatarRepo){
        return new AvatarsInteractor(jobExecutor,postExecutionThread,avatarRepo);
    }
}
