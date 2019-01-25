package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.FirstOpenAppInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.FirstCouponRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/12.
 */
public class GetFirstOpenAppInfoInteractor extends Interactor<FirstOpenAppInfo>{

    private final FirstCouponRepo firstCouponRepo;
    @Inject
    public GetFirstOpenAppInfoInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, FirstCouponRepo firstCouponRepo) {
        super(jobExecutor, postExecutionThread);
        this.firstCouponRepo = firstCouponRepo;
    }

    @Override
    protected Observable<FirstOpenAppInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<FirstOpenAppInfo>() {
            @Override
            public void call(Subscriber<? super FirstOpenAppInfo> subscriber) {
                FirstOpenAppInfo firstConpon = null;
                try {
                    firstConpon = firstCouponRepo.getFirstConpon();
                    subscriber.onNext(firstConpon);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
