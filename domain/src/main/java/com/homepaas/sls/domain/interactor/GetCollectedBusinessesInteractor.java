package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.BusinessCollectionEntity;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/23 0023
 *
 * @author zhudongjie .
 */
public class GetCollectedBusinessesInteractor extends Interactor<List<BusinessCollectionEntity>> {

    private BusinessInfoRepo businessInfoRepo;

    @Inject
    public GetCollectedBusinessesInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread
            , BusinessInfoRepo businessInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.businessInfoRepo = businessInfoRepo;
    }

    @Override
    protected Observable<List<BusinessCollectionEntity>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<BusinessCollectionEntity>>() {
            @Override
            public void call(Subscriber<? super List<BusinessCollectionEntity>> subscriber) {
                try {
                    List<BusinessCollectionEntity> collectedBusinessList = businessInfoRepo.getCollectedBusinessList();
                    subscriber.onNext(collectedBusinessList);
                    subscriber.onCompleted();
                } catch (GetDataException|AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
