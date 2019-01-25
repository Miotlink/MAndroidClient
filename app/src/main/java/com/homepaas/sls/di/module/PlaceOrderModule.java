package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.BusinessServiceTypeListInteractor;
import com.homepaas.sls.domain.interactor.CheckWorkerCallableInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessInfoInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerInfoInteractor;
import com.homepaas.sls.domain.interactor.WorkerServiceTypeListInteractor;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;
import com.homepaas.sls.domain.repository.ServiceTypeListRepo;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/7/1.
 */
@Module
public class PlaceOrderModule {

    @Provides
    @ActivityScope
    GetBusinessInfoInteractor provideGetBusinessInfoInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                               PostExecutionThread postExecutionThread,
                                                               BusinessInfoRepo businessInfoRepo) {
        return new GetBusinessInfoInteractor(jobExecutor, postExecutionThread, businessInfoRepo);
    }

    @Provides
    @ActivityScope
    GetWorkerInfoInteractor provideGetWorkerInfoInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                           PostExecutionThread postExecutionThread,
                                                           WorkerInfoRepo workerInfoRepo) {
        return new GetWorkerInfoInteractor(jobExecutor, postExecutionThread, workerInfoRepo);
    }

    @Provides
    @ActivityScope
    WorkerServiceTypeListInteractor provideWorkerServiceTypeInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, ServiceTypeListRepo serviceTypeListRepo) {
        return new WorkerServiceTypeListInteractor(jobExecutor,postExecutionThread,serviceTypeListRepo);
    }

    @Provides
    @ActivityScope
    BusinessServiceTypeListInteractor provideBusinessServiceTypeInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, ServiceTypeListRepo serviceTypeListRepo) {
        return new BusinessServiceTypeListInteractor(jobExecutor,postExecutionThread,serviceTypeListRepo);
    }

    @Provides
    @ActivityScope
    CheckWorkerCallableInteractor provideCheckWorkerCallableInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, WorkerInfoRepo workerInfoRepo){
        return new CheckWorkerCallableInteractor(jobExecutor,postExecutionThread,workerInfoRepo);
    }

}
