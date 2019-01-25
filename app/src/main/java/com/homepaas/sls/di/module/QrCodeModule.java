package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.AddShareRecordInteractor;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.domain.repository.ShareRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/7/13 0013
 *
 * @author zhudongjie
 */
@Module
public class QrCodeModule {

    @Provides
    @ActivityScope
    AddShareRecordInteractor provideAddShareRecordInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                             ShareRepo shareRepo){
        AddShareRecordInteractor interactor = new AddShareRecordInteractor(jobExecutor, postExecutionThread, shareRepo);
        interactor.setId(Constant.SHARE_TYPE_QRCODE);
        return interactor;
    }
}
