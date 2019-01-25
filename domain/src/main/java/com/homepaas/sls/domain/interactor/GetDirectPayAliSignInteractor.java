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
 * Created by Administrator on 2016/7/6.
 */
public class GetDirectPayAliSignInteractor extends Interactor<String> {


    String directPayId;
    String balancePayMoney;
    String alipayMoney;

    public void  setparams(String directPayId,String balancePayMoney){
        this.directPayId = directPayId;
        this.balancePayMoney = balancePayMoney;
    }


    @Inject
    OrderInfoRepo orderInfoRepo;

    @Inject
    public GetDirectPayAliSignInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String sign = orderInfoRepo.getDirectPayAliSign(directPayId,balancePayMoney);
                subscriber.onNext(sign);
                subscriber.onCompleted();
            }
        });
    }
}
