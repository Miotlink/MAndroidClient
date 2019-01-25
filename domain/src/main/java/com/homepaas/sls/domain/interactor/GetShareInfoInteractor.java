package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.ShareRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/7/1 0001
 *
 * @author zhudongjie
 */
public class GetShareInfoInteractor extends Interactor<ShareInfo>{

    private ShareRepo shareRepo;
    private int id;

    public GetShareInfoInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, ShareRepo shareRepo) {
        super(jobExecutor, postExecutionThread);
        this.shareRepo = shareRepo;

    }

    public void setId(int id) {
        this.id = id;
    }


    public void setShareRepo(ShareRepo shareRepo) {
        this.shareRepo = shareRepo;
    }

    @Override
    protected Observable<ShareInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<ShareInfo>() {
            @Override
            public void call(Subscriber<? super ShareInfo> subscriber) {
                try {
                    ShareInfo shareInfo = shareRepo.get(id);
                    subscriber.onNext(shareInfo);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
