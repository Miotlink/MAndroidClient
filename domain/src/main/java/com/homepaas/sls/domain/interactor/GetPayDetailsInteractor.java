package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PayDetail;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.AccountInfoRepo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/6/22 0022
 *
 * @author zhudongjie
 */
public class GetPayDetailsInteractor extends Interactor<List<PayDetail>>{

    private AccountInfoRepo accountInfoRepo;

    public GetPayDetailsInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AccountInfoRepo accountInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.accountInfoRepo = accountInfoRepo;
    }

    @Override
    protected Observable<List<PayDetail>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<PayDetail>>() {
            @Override
            public void call(Subscriber<? super List<PayDetail>> subscriber) {
                try {
                    List<PayDetail> payDetails = accountInfoRepo.getPayDetails();
                    subscriber.onNext(payDetails);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
