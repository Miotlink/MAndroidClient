package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.ShareRepo;

import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Xiejiali on 2016/7/8.
 */
public class AddShareRecordInteractor extends Interactor<String> {

    ShareRepo shareRepo;
    int id;

    public AddShareRecordInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, ShareRepo shareRepo) {
        super(jobExecutor, postExecutionThread);
        this.shareRepo = shareRepo;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = shareRepo.addShareRecord(id);
                    subscriber.onNext(result);
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }

            }
        });
    }

    public void setId(int id) {
        this.id = id;
    }
}
