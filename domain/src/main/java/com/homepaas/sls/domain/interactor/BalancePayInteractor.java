package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.OrderInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/5.
 */
public class BalancePayInteractor extends Interactor<String> {

    @Inject
    OrderInfoRepo orderInfoRepo;
    String orderId;
    String couponId;
    String balanceMoney;

    @Inject
    public BalancePayInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    public void setParam(String orderId,String couponId,String balanceMoney){
        this.orderId = orderId;
        this.couponId = couponId;
        this.balanceMoney = balanceMoney;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s = orderInfoRepo.payOrderByBalance(orderId, couponId, balanceMoney);
                subscriber.onNext(s);
            }
        });
    }
}
