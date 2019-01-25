package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.AccountInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.AccountInfoRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public class GetAccountInfoInteractor extends Interactor<AccountInfo>{

    private AccountInfoRepo accountInfoRepo;

    public GetAccountInfoInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AccountInfoRepo accountInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.accountInfoRepo = accountInfoRepo;
    }

    @Override
    protected Observable<AccountInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<AccountInfo>() {
            @Override
            public void call(Subscriber<? super AccountInfo> subscriber) {
                try {
                    AccountInfo info = accountInfoRepo.get();
                    subscriber.onNext(info);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
