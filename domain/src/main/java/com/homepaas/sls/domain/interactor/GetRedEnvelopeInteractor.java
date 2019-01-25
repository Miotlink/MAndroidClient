package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.CouponContentsInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.RedEnvelopeRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/6/21.
 */
public class GetRedEnvelopeInteractor extends Interactor<CouponContentsInfo> {

    private RedEnvelopeRepo redEnvelopeRepo;
    private String status;

    @Inject
    public GetRedEnvelopeInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, RedEnvelopeRepo redEnvelopeRepo) {
        super(jobExecutor, postExecutionThread);
        this.redEnvelopeRepo = redEnvelopeRepo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    protected Observable<CouponContentsInfo> buildObservable() {
       return Observable.create(new Observable.OnSubscribe<CouponContentsInfo>() {
           @Override
           public void call(Subscriber<? super CouponContentsInfo> subscriber) {
               CouponContentsInfo couponContentsInfo = null;
               try {
                   couponContentsInfo = redEnvelopeRepo.getRedEnvelopeInfo(status);
                   subscriber.onNext(couponContentsInfo);
                   subscriber.onCompleted();
               } catch (GetDataException | AuthException e) {
                   subscriber.onError(e);
                   //e.printStackTrace();
               }

           }
       });
    }
}
