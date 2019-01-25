package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.UploadQrCodeInteractor;
import com.homepaas.sls.domain.repository.OtherRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/7/12 0012
 *
 * @author zhudongjie
 */
@Module
public class QrScanModule {

    @ActivityScope
    @Provides
    UploadQrCodeInteractor provideUploadQrCodeInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                                         OtherRepo otherRepo){
        return new UploadQrCodeInteractor(jobExecutor,postExecutionThread,otherRepo);
    }
}
