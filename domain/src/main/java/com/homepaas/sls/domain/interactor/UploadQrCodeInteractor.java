package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.OtherRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/7/11 0011
 *
 * @author zhudongjie
 */
public class UploadQrCodeInteractor extends Interactor<PushInfo>{

    private OtherRepo otherRepo;
    private String url;

    public UploadQrCodeInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, OtherRepo otherRepo) {
        super(jobExecutor, postExecutionThread);
        this.otherRepo = otherRepo;
    }

    @Override
    protected Observable<PushInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<PushInfo>() {
            @Override
            public void call(Subscriber<? super PushInfo> subscriber) {

                try {
                    PushInfo pushInfo = otherRepo.loadQrCodeUrl(url);
                    subscriber.onNext(pushInfo);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                   subscriber.onError(e);
                }
            }
        });
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
