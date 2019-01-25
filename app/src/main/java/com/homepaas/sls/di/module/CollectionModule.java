package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.CollectBusinessInteractor;
import com.homepaas.sls.domain.interactor.CollectWorkerInteractor;
import com.homepaas.sls.domain.interactor.GetCollectedBusinessesInteractor;
import com.homepaas.sls.domain.interactor.GetCollectedWorkersInteractor;
import com.homepaas.sls.domain.interactor.LikeBusinessInteractor;
import com.homepaas.sls.domain.interactor.LikeWorkerInteractor;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
@Module
public class CollectionModule {

    @Provides
    @ActivityScope
    GetCollectedBusinessesInteractor provideGetCollectedBusinessesInteractor(@Named("concurrent") JobExecutor jobExecutor
            , PostExecutionThread postExecutionThread, BusinessInfoRepo businessInfoRepo) {
        return new GetCollectedBusinessesInteractor(jobExecutor, postExecutionThread, businessInfoRepo);
    }

    @Provides
    @ActivityScope
    GetCollectedWorkersInteractor provideGetCollectedWorkersInteractor(@Named("concurrent") JobExecutor jobExecutor
            , PostExecutionThread postExecutionThread, WorkerInfoRepo workerInfoRepo) {
        return new GetCollectedWorkersInteractor(jobExecutor, postExecutionThread, workerInfoRepo);
    }

    @Provides
    @ActivityScope
    LikeBusinessInteractor provideLikeBusinessInteractor(@Named("concurrent") JobExecutor jobExecutor
            , PostExecutionThread postExecutionThread,BusinessInfoRepo businessInfoRepo) {
        return new LikeBusinessInteractor(jobExecutor, postExecutionThread, businessInfoRepo);
    }

    @Provides
    @ActivityScope
    LikeWorkerInteractor provideLikeWorkerInteractor(@Named("concurrent") JobExecutor jobExecutor
            , PostExecutionThread postExecutionThread, WorkerInfoRepo workerInfoRepo) {
        return new LikeWorkerInteractor(jobExecutor, postExecutionThread,workerInfoRepo);
    }

    @Provides
    @ActivityScope
    CollectBusinessInteractor provideCollectBusinessInteractor(@Named("concurrent") JobExecutor jobExecutor
            , PostExecutionThread postExecutionThread,BusinessInfoRepo businessInfoRepo) {
        return new CollectBusinessInteractor(jobExecutor, postExecutionThread,businessInfoRepo);
    }

    @Provides
    @ActivityScope
    CollectWorkerInteractor provideCollectWorkerInteractor(@Named("concurrent") JobExecutor jobExecutor
            , PostExecutionThread postExecutionThread, WorkerInfoRepo workerInfoRepo) {
        return new CollectWorkerInteractor(jobExecutor, postExecutionThread,workerInfoRepo);
    }
}
