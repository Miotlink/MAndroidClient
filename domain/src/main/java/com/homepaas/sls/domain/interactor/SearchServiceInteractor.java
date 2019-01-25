package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.NoServiceException;
import com.homepaas.sls.domain.exception.OutOfServiceException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.param.SearchParameter;
import com.homepaas.sls.domain.repository.SearchRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CJJ on 2016/9/8.
 */
public class SearchServiceInteractor extends Interactor<ServiceSearchInfo>{

    @Inject
    SearchRepo searchRepo;
    SearchParameter searchParameter;

    public void setSearchParameter(SearchParameter searchParameter) {
        this.searchParameter = searchParameter;
    }

    @Inject
    public SearchServiceInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<ServiceSearchInfo> buildObservable() {
       return Observable.create(new Observable.OnSubscribe<ServiceSearchInfo>() {
            @Override
            public void call(Subscriber<? super ServiceSearchInfo> subscriber) {
                try {
                    ServiceSearchInfo serviceSearchInfo = searchRepo.searchService(searchParameter);
                    subscriber.onNext(serviceSearchInfo);
                } catch (AuthException e) {
                    subscriber.onError(e);
                } catch (GetDataException e) {
                    subscriber.onError(e);
                } catch (OutOfServiceException e) {
                    subscriber.onError(e);
                } catch (NoServiceException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
