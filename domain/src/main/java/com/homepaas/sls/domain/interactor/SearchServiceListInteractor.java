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
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/14 0014
 *
 * @author zhudongjie .
 */
@Singleton
public class SearchServiceListInteractor extends Interactor<ServiceSearchInfo> {

    private SearchRepo searchListRepo;

    private SearchParameter searchParameter;

    @Inject
    public SearchServiceListInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread
            , SearchRepo searchListRepo) {
        super(jobExecutor, postExecutionThread);
        this.searchListRepo = searchListRepo;
    }

    public void setSearchParameter(SearchParameter searchParameter) {
        this.searchParameter = searchParameter;
    }

    @Override
    protected Observable<ServiceSearchInfo> buildObservable() {

        return Observable.create(new Observable.OnSubscribe<ServiceSearchInfo>() {

            @Override
            public void call(Subscriber<? super ServiceSearchInfo> subscriber) {
                    ServiceSearchInfo serviceInfo ;
                    try {
                        serviceInfo = searchListRepo.searchService(searchParameter);
                        subscriber.onNext(serviceInfo);
                        subscriber.onCompleted();
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
