package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.BalanceInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.AccountInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CMJ on 2016/6/25.
 *
 */

public class GetAccountBalanceInteractor extends Interactor<BalanceInfo> {

    @Inject
    AccountInfoRepo accountInfoRepo;

    @Inject
    public GetAccountBalanceInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }


    @Override
    protected Observable<BalanceInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<BalanceInfo>() {
            @Override
            public void call(Subscriber<? super BalanceInfo> subscriber) {
                BalanceInfo accountBalanceInfo = null;
                try {
                    accountBalanceInfo = accountInfoRepo.getAccountBalanceInfo();
                    subscriber.onNext(accountBalanceInfo);
                } catch (AuthException e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
