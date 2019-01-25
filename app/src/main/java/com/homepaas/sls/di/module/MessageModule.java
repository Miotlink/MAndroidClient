package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.DeleteMessageInteractor;
import com.homepaas.sls.domain.interactor.GetMessageListInteractor;
import com.homepaas.sls.domain.interactor.GetUnreadMessageCountInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.interactor.ReadMessageInteractor;
import com.homepaas.sls.domain.repository.MessageRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
@Module
public class MessageModule {

    @Provides
    @ActivityScope
    @Named("GetMessageListInteractor")
    Interactor provideGetMessageListInteractor(GetMessageListInteractor getMessageListInteractor) {
        return getMessageListInteractor;
    }

    @Provides
    @ActivityScope
    DeleteMessageInteractor provideDeleteMessageInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                           PostExecutionThread postExecutionThread, MessageRepo messageRepo) {
        return new DeleteMessageInteractor(jobExecutor,postExecutionThread,messageRepo);
    }

    @Provides
    @ActivityScope
    @Named("UnreadMessageInteractor")
    Interactor provideUnreadMessageCountInteractor(GetUnreadMessageCountInteractor interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    ReadMessageInteractor provideReadMessageInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                                       PostExecutionThread postExecutionThread, MessageRepo messageRepo){
        return new ReadMessageInteractor(jobExecutor,postExecutionThread,messageRepo);
    }
}
