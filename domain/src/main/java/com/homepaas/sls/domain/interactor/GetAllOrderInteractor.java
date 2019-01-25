package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.OrderInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.OrderInfoRepo;


import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/5/3.
 */
public class GetAllOrderInteractor extends Interactor<OrderInfo>{

    @Inject
    OrderInfoRepo orderInfoRepo;

    private String pageSize;
    private String pageIndex;
    private String type;
    private boolean fresh;

    public void setParams(String pageSize, String pageIndex, String type) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.type = type;
    }

    @Inject
    public GetAllOrderInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }


    @Override
    protected Observable<OrderInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<OrderInfo>() {

            @Override
            public void call(Subscriber<? super OrderInfo> subscriber) {
                OrderInfo orderInfo;
                try {
                    try {
                        orderInfo = orderInfoRepo.getAllOrder(fresh);
                        subscriber.onNext(orderInfo);
                        subscriber.onCompleted();
                    } catch (AuthException e) {
                       subscriber.onError(e);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }
}
