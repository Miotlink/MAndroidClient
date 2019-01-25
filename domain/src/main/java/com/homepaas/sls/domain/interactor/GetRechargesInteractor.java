package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.Recharge;
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
public class GetRechargesInteractor extends Interactor<List<Recharge>> {

    private AccountInfoRepo accountInfoRepo;

    public GetRechargesInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AccountInfoRepo accountInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.accountInfoRepo = accountInfoRepo;
    }

    @Override
    protected Observable<List<Recharge>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<Recharge>>() {
            @Override
            public void call(Subscriber<? super List<Recharge>> subscriber) {
                try {
                    List<Recharge> recharges = accountInfoRepo.getRecharges();
                    subscriber.onNext(recharges);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
