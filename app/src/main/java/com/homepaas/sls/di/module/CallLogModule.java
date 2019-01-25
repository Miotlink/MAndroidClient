package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.CheckBusinessCallableInteractor;
import com.homepaas.sls.domain.interactor.CheckWorkerCallableInteractor;
import com.homepaas.sls.domain.interactor.DeleteCallLogsInteractor;
import com.homepaas.sls.domain.interactor.GetCallLogInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.interactor.SaveCallLogInteractor;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;
import com.homepaas.sls.domain.repository.CallLogRepo;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
@Module
public class CallLogModule {

    @Provides
    @ActivityScope
    @Named("GetCallLogInteractor")
    Interactor provideGetCallLogInteractor(GetCallLogInteractor getCallLogInteractor) {
        return getCallLogInteractor;
    }

    @Provides
    @ActivityScope
    SaveCallLogInteractor provideSaveCallLogInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                       PostExecutionThread postExecutionThread,
                                                       CallLogRepo callLogRepo) {
        return new SaveCallLogInteractor(jobExecutor, postExecutionThread, callLogRepo);
    }

    @Provides
    @ActivityScope
    DeleteCallLogsInteractor provideCallLogsInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                       CallLogRepo callLogRepo) {
        return new DeleteCallLogsInteractor(jobExecutor, postExecutionThread, callLogRepo);
    }



}
