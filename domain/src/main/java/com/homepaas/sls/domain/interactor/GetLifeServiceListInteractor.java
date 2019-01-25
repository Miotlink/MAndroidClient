package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.LifeService;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.LifeServiceRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/1/14.
 * 获取
 */

public class GetLifeServiceListInteractor extends Interactor<List<LifeService>> {

    private LifeServiceRepo repo;

    @Inject
    public GetLifeServiceListInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                        PostExecutionThread postExecutionThread,
                                        LifeServiceRepo repo) {
        super(jobExecutor, postExecutionThread);
        this.repo = repo;
    }

    @Override
    protected Observable<List<LifeService>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<LifeService>>() {
            @Override
            public void call(Subscriber<? super List<LifeService>> subscriber) {
                List<LifeService> services = null;
                try {
                    services = repo.getLifeServiceList();
                    subscriber.onNext(services);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                   subscriber.onError(e);
                }

            }
        });
    }
}
