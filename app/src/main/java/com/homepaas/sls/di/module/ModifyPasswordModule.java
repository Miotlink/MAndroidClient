package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.ModifyPasswordInteractor;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/2/18 0018
 *
 * @author zhudongjie .
 */
@Module
public class ModifyPasswordModule {

    @ActivityScope
    @Provides
    ModifyPasswordInteractor provideModifyPasswordInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                             PersonalInfoRepo personalInfoRepo) {
        return new ModifyPasswordInteractor(jobExecutor, postExecutionThread, personalInfoRepo);
    }
}
