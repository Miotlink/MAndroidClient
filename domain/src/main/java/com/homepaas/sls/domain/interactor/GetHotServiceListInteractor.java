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
 * on 2016/2/14 0014
 *
 * @author zhudongjie .
 */
public class GetHotServiceListInteractor extends Interactor<List<LifeService>> {

    private LifeServiceRepo repo;

    @Inject
    public GetHotServiceListInteractor(@Named("concurrent") JobExecutor jobExecutor,
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
                try {
                    List<LifeService> serviceList = repo.getHotLifeServiceList();
                    subscriber.onNext(serviceList);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
