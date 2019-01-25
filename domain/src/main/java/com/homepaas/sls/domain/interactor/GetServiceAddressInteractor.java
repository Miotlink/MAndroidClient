package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.AddressRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;



/**
 * Created by CJJ on 2016/9/13.
 */

public class GetServiceAddressInteractor extends Interactor<List<AddressEntity>> {

    @Inject
    AddressRepo addressRepo;

    @Inject
    public GetServiceAddressInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }



    @Override
    protected Observable<List<AddressEntity>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<AddressEntity>>() {
            @Override
            public void call(Subscriber<? super List<AddressEntity>> subscriber) {
                try {
                    List<AddressEntity> addresses = addressRepo.getAddress();
                    subscriber.onNext(addresses);
                } catch (AuthException e) {
                   subscriber.onError(e);
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
