package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.AddRecommendInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Sherily on 2016/10/17.
 */
public class AddRecommendInfoInteractor extends Interactor<String> {

    private AddRecommendInfoRepo addRecommendInfoRepo;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Inject
    public AddRecommendInfoInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AddRecommendInfoRepo addRecommendInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.addRecommendInfoRepo = addRecommendInfoRepo;
    }


    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String msg = addRecommendInfoRepo.addRecommendInfo(getCode());
                    subscriber.onNext(msg);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                } catch (AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
