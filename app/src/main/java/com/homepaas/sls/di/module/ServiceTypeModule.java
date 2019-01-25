package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.BusinessServiceTypeListInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.interactor.WorkerServiceTypeListInteractor;
import com.homepaas.sls.domain.repository.ServiceTypeListRepo;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/4/27.
 */
@Module
public class ServiceTypeModule {

    @Provides
    @ActivityScope
    @Named("WorkerServiceTypeListInteractor")
    Interactor provideWorkerServiceTypeListInteractor(WorkerServiceTypeListInteractor interactor){

        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("BusinessServiceTypeListInteractor")
    Interactor provideBusinessServiceTypeListInteractor(BusinessServiceTypeListInteractor interactor){

        return interactor;
    }

}
