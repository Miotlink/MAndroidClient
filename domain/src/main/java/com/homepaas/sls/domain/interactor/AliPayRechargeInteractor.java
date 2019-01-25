package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.param.RechargePaySignParam;
import com.homepaas.sls.domain.repository.AccountInfoRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/6/28 0028
 *
 * @author zhudongjie
 */
public class AliPayRechargeInteractor extends Interactor<String>{

    private AccountInfoRepo accountInfoRepo;
    private RechargePaySignParam param;

    public AliPayRechargeInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AccountInfoRepo accountInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.accountInfoRepo = accountInfoRepo;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String alipaySigh = accountInfoRepo.getAlipaySigh(param);
                    subscriber.onNext(alipaySigh);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public void setParam(RechargePaySignParam param) {
        this.param = param;
    }
}
