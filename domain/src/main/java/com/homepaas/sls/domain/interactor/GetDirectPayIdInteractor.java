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
public class GetDirectPayIdInteractor extends Interactor<String> {


    String receiverId;
    String receiverType;
    String paySource;
    String TotalMoney;
    String ServiceTypeID;

    @Inject
    OrderInfoRepo orderInfoRepo;

   public void  setParams(String receiverId,String receiverType,String paySource,String TotalMoney,String ServiceTypeID){
        this.receiverId = receiverId;
       this.receiverType = receiverType;
       this.paySource = paySource;
       this.TotalMoney = TotalMoney;
       this.ServiceTypeID = ServiceTypeID;
    }

    @Inject
    public GetDirectPayIdInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String directPayId = orderInfoRepo.getDirectPayId(receiverId, receiverType, paySource, TotalMoney, ServiceTypeID);
                subscriber.onNext(directPayId);
                subscriber.onCompleted();
            }
        });
    }
}
