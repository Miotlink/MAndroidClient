package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.AddShareRecordInteractor;
import com.homepaas.sls.domain.interactor.AvatarsInteractor;
import com.homepaas.sls.domain.interactor.CheckBusinessCallableInteractor;
import com.homepaas.sls.domain.interactor.CollectBusinessInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessEvaluationsInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessInfoInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessServiceContentsInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessTagsInfoInteractor;
import com.homepaas.sls.domain.interactor.GetShareInfoInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerTagsInfoInteractor;
import com.homepaas.sls.domain.interactor.LikeBusinessInteractor;
import com.homepaas.sls.domain.interactor.ReportBusinessInteractor;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.domain.repository.AvatarRepo;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;
import com.homepaas.sls.domain.repository.BusinessTagsRepo;
import com.homepaas.sls.domain.repository.ShareRepo;
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
public class BusinessDetailModule {

    @Provides
    @ActivityScope
    GetBusinessInfoInteractor provideGetBusinessInfoInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                               PostExecutionThread postExecutionThread,
                                                               BusinessInfoRepo businessInfoRepo) {
        return new GetBusinessInfoInteractor(jobExecutor, postExecutionThread, businessInfoRepo);
    }

    @Provides
    @ActivityScope
    LikeBusinessInteractor provideLikeBusinessInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                         PostExecutionThread postExecutionThread,
                                                         BusinessInfoRepo businessInfoRepo) {
        return new LikeBusinessInteractor(jobExecutor, postExecutionThread, businessInfoRepo);
    }

    @Provides
    @ActivityScope
    CollectBusinessInteractor provideCollectWorkerInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                             PostExecutionThread postExecutionThread,
                                                             BusinessInfoRepo businessInfoRepo) {
        return new CollectBusinessInteractor(jobExecutor, postExecutionThread, businessInfoRepo);
    }

    @Provides
    @ActivityScope
    CheckBusinessCallableInteractor provideCheckBusinessCallableInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                                           PostExecutionThread postExecutionThread,
                                                                           BusinessInfoRepo businessInfoRepo) {
        return new CheckBusinessCallableInteractor(jobExecutor, postExecutionThread, businessInfoRepo);
    }

    @Provides
    @ActivityScope
    GetBusinessEvaluationsInteractor provideGetBusinessEvaluationsInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                                             PostExecutionThread postExecutionThread,
                                                                             BusinessInfoRepo businessInfoRepo) {
        return new GetBusinessEvaluationsInteractor(jobExecutor,postExecutionThread,businessInfoRepo);
    }

    @Provides
    @ActivityScope
    GetBusinessServiceContentsInteractor provideGetBusinessServiceContentsInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                                                     PostExecutionThread postExecutionThread,
                                                                                     BusinessInfoRepo businessInfoRepo) {
        return new GetBusinessServiceContentsInteractor(jobExecutor,postExecutionThread,businessInfoRepo);
    }

    @Provides
    @ActivityScope
    ReportBusinessInteractor provideReportBusinessInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                             PostExecutionThread postExecutionThread,
                                                             BusinessInfoRepo businessInfoRepo) {
        return new ReportBusinessInteractor(jobExecutor,postExecutionThread,businessInfoRepo);
    }

//    @Provides
//    @ActivityScope
//    GetShareInfoInteractor provideGetShareInfoInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
//                                                         ShareRepo shareRepo){
//            GetShareInfoInteractor interactor = new GetShareInfoInteractor(jobExecutor, postExecutionThread, shareRepo);
//            interactor.setId(Constant.SHARE_TYPE_WORKER_BUSINESS_MAIN_PAGE);
//            return interactor;
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

    @Provides
    @ActivityScope
    GetBusinessTagsInfoInteractor provideGetBusinessTagsInfoInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                                       BusinessTagsRepo businessTagsRepo){
        return new GetBusinessTagsInfoInteractor(jobExecutor,postExecutionThread,businessTagsRepo);
    }

//    @Provides
//    @ActivityScope
//    AvatarsInteractor providerAvatarsInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AvatarRepo avatarRepo){
//        return new AvatarsInteractor(jobExecutor,postExecutionThread,avatarRepo);
//    }
}
