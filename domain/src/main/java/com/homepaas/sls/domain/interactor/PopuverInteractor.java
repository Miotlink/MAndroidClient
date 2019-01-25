package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PopupVer;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.PopuverRepo;

import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Sherily on 2016/12/27.
 */

public class PopuverInteractor extends Interactor<PopupVer> {
    PopuverRepo popuverRepo;

    public PopuverInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, PopuverRepo popuverRepo) {
        super(jobExecutor, postExecutionThread);
        this.popuverRepo = popuverRepo;
    }

    @Override
    protected Observable<PopupVer> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<PopupVer>() {
            @Override
            public void call(Subscriber<? super PopupVer> subscriber) {
                try {
                    PopupVer popupVer = popuverRepo.getPopuVer();
                    subscriber.onNext(popupVer);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                } catch (AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
