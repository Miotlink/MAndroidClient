package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.WxSign;
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
public class GetDirectPayWxSignInteractor extends Interactor<WxSign>{

    String directPayId;
    String balancePayMoney;

   public void  setparams(String directPayId,String balancePayMoney){
       this.directPayId = directPayId;
       this.balancePayMoney = balancePayMoney;
    }

    @Inject
    OrderInfoRepo orderInfoRepo;

    @Inject
    public GetDirectPayWxSignInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<WxSign> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<WxSign>() {
            @Override
            public void call(Subscriber<? super WxSign> subscriber) {
                WxSign wxSign = orderInfoRepo.getDirectPayWXSign(directPayId,balancePayMoney);
                subscriber.onNext(wxSign);
                subscriber.onCompleted();
            }
        });
    }
}
