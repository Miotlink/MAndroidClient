package com.homepaas.sls.di.module;

import com.homepaas.sls.pushservice.PushUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/7/10.
 */
@Module
public class PushModule {

    @Singleton
    @Provides
    PushUtil providePushUtil(){
        return new PushUtil();
    }



//    @ActivityScope
//    @Provides
//    UploadClientIdInteractor provideUploadCleintIdInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, PushRepo pushRepo){
//        return new UploadClientIdInteractor(jobExecutor,postExecutionThread,pushRepo);
//    }
//
//    @Provides
//    @ActivityScope
//    PushPresenter  providerPushPresenter(){
//        return new PushPresenter();
//    }
}
