package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.AddShareRecordInteractor;
import com.homepaas.sls.domain.interactor.AvatarsInteractor;
import com.homepaas.sls.domain.interactor.CheckWorkerCallableInteractor;
import com.homepaas.sls.domain.interactor.CollectWorkerInteractor;
import com.homepaas.sls.domain.interactor.GetShareInfoInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerEvaluationsInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerInfoInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerTagsInfoInteractor;
import com.homepaas.sls.domain.interactor.LikeWorkerInteractor;
import com.homepaas.sls.domain.interactor.ReportWorkerInteractor;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.domain.repository.AvatarRepo;
import com.homepaas.sls.domain.repository.ShareRepo;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;
import com.homepaas.sls.domain.repository.WorkerTagsRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/2/1 0001
 *
 * @author zhudongjie .
 */
@Module
public class WorkerDetailModule {

    @Provides
    @ActivityScope
    GetWorkerInfoInteractor provideGetWorkerInfoInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                           PostExecutionThread postExecutionThread,
                                                           WorkerInfoRepo workerInfoRepo) {
        return new GetWorkerInfoInteractor(jobExecutor, postExecutionThread, workerInfoRepo);
    }

    @Provides
    @ActivityScope
    GetWorkerTagsInfoInteractor provideGetWorkerTagsInfoInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                                   PostExecutionThread postExecutionThread,
                                                                   WorkerTagsRepo workerTagsRepo){
        return new GetWorkerTagsInfoInteractor(jobExecutor, postExecutionThread,workerTagsRepo);
    }

    @Provides
    @ActivityScope
    LikeWorkerInteractor provideLikeWorkerInteractor(@Named("concurrent")JobExecutor jobExecutor,
                                                     PostExecutionThread postExecutionThread,
                                                     WorkerInfoRepo workerInfoRepo){
        return new LikeWorkerInteractor(jobExecutor,postExecutionThread,workerInfoRepo);
    }

    @Provides
    @ActivityScope
    CollectWorkerInteractor provideCollectWorkerInteractor(@Named("concurrent")JobExecutor jobExecutor,
                                                           PostExecutionThread postExecutionThread,
                                                           WorkerInfoRepo workerInfoRepo){
        return new CollectWorkerInteractor(jobExecutor,postExecutionThread,workerInfoRepo);
    }

    @Provides
    @ActivityScope
    CheckWorkerCallableInteractor provideCheckWorkerCallableInteractor(@Named("concurrent")JobExecutor jobExecutor,
                                                                       PostExecutionThread postExecutionThread,
                                                                       WorkerInfoRepo workerInfoRepo){
        return new CheckWorkerCallableInteractor(jobExecutor,postExecutionThread,workerInfoRepo);
    }

    @Provides
    @ActivityScope
    GetWorkerEvaluationsInteractor provideGetWorkerEvaluationsInteractor(@Named("concurrent")JobExecutor jobExecutor,
                                                                         PostExecutionThread postExecutionThread,
                                                                         WorkerInfoRepo workerInfoRepo){
        return new GetWorkerEvaluationsInteractor(jobExecutor,postExecutionThread,workerInfoRepo);
    }

    @Provides
    @ActivityScope
    ReportWorkerInteractor provideReportWorkerInteractor(@Named("concurrent")JobExecutor jobExecutor,
                                                         PostExecutionThread postExecutionThread,
                                                         WorkerInfoRepo workerInfoRepo){
        return new ReportWorkerInteractor(jobExecutor,postExecutionThread,workerInfoRepo);
    }

//    @Provides
//    @ActivityScope
//    GetShareInfoInteractor provideGetShareInfoInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
//                                                         ShareRepo shareRepo){
//        GetShareInfoInteractor interactor = new GetShareInfoInteractor(jobExecutor, postExecutionThread, shareRepo);
//        interactor.setId(Constant.SHARE_TYPE_WORKER_BUSINESS_MAIN_PAGE);
//        return interactor;
//    }
//
//    @Provides
//    @ActivityScope
//    AddShareRecordInteractor provideAddShareRecordInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
//                                                             ShareRepo shareRepo){
//        AddShareRecordInteractor interactor = new AddShareRecordInteractor(jobExecutor, postExecutionThread, shareRepo);
//        interactor.setId(Constant.SHARE_TYPE_WORKER_BUSINESS_MAIN_PAGE);
//        return interactor;
//    }

//    @Provides
//    @ActivityScope
//    AvatarsInteractor providerAvatarsInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AvatarRepo avatarRepo){
//        return new AvatarsInteractor(jobExecutor,postExecutionThread,avatarRepo);
//    }
}
