package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.BannersInfoRepo;

import java.util.List;

import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Sherily on 2016/9/10.
 */
public class BannersInfoInteractor extends Interactor<List<BannerInfo>> {

   private BannersInfoRepo bannersInfoRepo;

    public BannersInfoInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, BannersInfoRepo bannersInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.bannersInfoRepo = bannersInfoRepo;
    }

    @Override
    protected Observable<List<BannerInfo>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<BannerInfo>>() {
            @Override
            public void call(Subscriber<? super List<BannerInfo>> subscriber) {
                try {
                    List<BannerInfo> bannerInfos = bannersInfoRepo.getBannersInfo();
                    subscriber.onNext(bannerInfos);
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
