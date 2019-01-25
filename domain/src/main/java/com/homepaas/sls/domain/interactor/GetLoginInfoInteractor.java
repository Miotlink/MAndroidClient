package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.Account;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.LoginInfoRepo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/6/7 0007
 *
 * @author zhudongjie
 */
public class GetLoginInfoInteractor extends Interactor<List<Account>>{

    private LoginInfoRepo loginInfoRepo;

    public GetLoginInfoInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, LoginInfoRepo loginInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.loginInfoRepo = loginInfoRepo;
    }

    @Override
    protected Observable<List<Account>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<Account>>() {
            @Override
            public void call(Subscriber<? super List<Account>> subscriber) {
                try {
                    List<Account> accountList = loginInfoRepo.getAccountList();
                    subscriber.onNext(accountList);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
