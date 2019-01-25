package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.AddEvaluationInteractor;
import com.homepaas.sls.domain.interactor.EvaluateOrderInteractor;
import com.homepaas.sls.domain.repository.CommentRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * on 2016/6/24 0024
 *
 * @author zhudongjie
 */
@Module
public class EditCommentModule {

    private  String orderId;

    private  String type;

    private  String id;

    public EditCommentModule(String orderId, String type, String id) {
        this.orderId = orderId;
        this.type = type;
        this.id = id;
    }


    @Provides
    @ActivityScope
    public AddEvaluationInteractor provideAddEvaluationInteractor(@Named("concurrent")JobExecutor jobExecutor,
                                                                  PostExecutionThread postExecutionThread,
                                                                  CommentRepo commentRepo){
        AddEvaluationInteractor interactor  = new AddEvaluationInteractor(jobExecutor,postExecutionThread,commentRepo);
        interactor.setOrderId(orderId);
        interactor.setOwnerId(id);
        interactor.setType(type);
        return interactor;
    }


    @Provides
    @ActivityScope
    public EvaluateOrderInteractor provideEvaluateOrderInteractor(@Named("concurrent")JobExecutor jobExecutor,
                                                                  PostExecutionThread postExecutionThread,
                                                                  CommentRepo commentRepo){
        EvaluateOrderInteractor interactor  = new EvaluateOrderInteractor(jobExecutor,postExecutionThread,commentRepo);
        interactor.setOrderId(orderId);
        return interactor;
    }
}
