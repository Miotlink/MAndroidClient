package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.AccountDetail;
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
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public class GetAccountDetailsInteractor extends Interactor<List<AccountDetail>>{

    private AccountInfoRepo accountInfoRepo;

    public GetAccountDetailsInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AccountInfoRepo accountInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.accountInfoRepo = accountInfoRepo;
    }

    @Override
    protected Observable<List<AccountDetail>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<AccountDetail>>() {
            @Override
            public void call(Subscriber<? super List<AccountDetail>> subscriber) {
                try {
                    List<AccountDetail> info = accountInfoRepo.getDetails();
                    subscriber.onNext(info);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
