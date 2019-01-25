package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.AdsInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.AdsRepo;

import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/27.
 */

public class AdsInfoInteractor extends Interactor<AdsInfo> {
    private AdsRepo adsRepo;

    public AdsInfoInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AdsRepo adsRepo) {
        super(jobExecutor, postExecutionThread);
        this.adsRepo = adsRepo;
    }

    @Override
    protected Observable<AdsInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<AdsInfo>() {
            @Override
            public void call(Subscriber<? super AdsInfo> subscriber) {
                try {
                    AdsInfo adsInfo = adsRepo.getAdsInfo();
                    subscriber.onNext(adsInfo);
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
