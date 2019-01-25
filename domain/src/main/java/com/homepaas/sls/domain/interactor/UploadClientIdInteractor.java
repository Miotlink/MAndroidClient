package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.PushRepo;

import javax.inject.Inject;
import javax.inject.Named;

//import javafx.beans.NamedArg;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by CJJ on 2016/7/29.
 */
public class UploadClientIdInteractor extends Interactor<String>  {


    PushRepo pushRepo;
    String clientId;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Inject
    public UploadClientIdInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, PushRepo pushRepo) {
        super(jobExecutor, postExecutionThread);
        this.pushRepo = pushRepo;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                pushRepo.uploadPushCleintId(clientId);
            }
        });
    }
}
