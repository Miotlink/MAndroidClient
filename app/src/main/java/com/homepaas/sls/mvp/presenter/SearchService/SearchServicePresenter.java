package com.homepaas.sls.mvp.presenter.SearchService;

import android.support.annotation.StringDef;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.HotServiceInfo;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.repository.HotServiceInfoRepo;
import com.homepaas.sls.domain.repository.SearchServiceRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.SearchService.SearchServiceView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_DEFAULT;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_TEXT;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_TYPE;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_VOICE;

/**
 * Created by JWC on 2016/12/20.
 */

@ActivityScope
public class SearchServicePresenter implements Presenter {


    @StringDef({QUERY_MODE_TEXT, QUERY_MODE_VOICE, QUERY_MODE_TYPE, QUERY_MODE_DEFAULT})
    public @interface QueryType {
    }

    @Inject
    public SearchServicePresenter() {
    }
    private SearchServiceView searchServiceView;
    public void setSearchServiceView(SearchServiceView searchServiceView){
        this.searchServiceView=searchServiceView;    }

    @Inject
    SearchServiceRepo searchServiceRepo;

    @Inject
    HotServiceInfoRepo hotServiceInfoRepo;


    private Observable<ServiceSearchInfo> mObservable;
    private List<Subscription> subscriptionList=new ArrayList<>();


    public void getSearchServiceList(String latitude, String longitude, String type, String queryStr, String queryType, String serviceId){
        for (Subscription subscription:subscriptionList){
            subscription.unsubscribe();
        }
        mObservable=searchServiceRepo.getSearchService(latitude,longitude,type,queryStr,queryType,serviceId)
                .compose(RxUtil.<ServiceSearchInfo>applySchedulers());
                Subscription mSubscription= mObservable.subscribe(new OldBaseObserver<ServiceSearchInfo>(searchServiceView) {
                    @Override
                    public void onNext(ServiceSearchInfo serviceSearchInfo) {
                        searchServiceView.render(serviceSearchInfo);

                    }
                });
        subscriptionList.add(mSubscription);
    }


    public void rerutnBackMap(String latitude, String longitude, String type, String queryStr, String queryType, String serviceId){
        searchServiceView.showLoading();
        searchServiceRepo.getSearchService(latitude,longitude,type,queryStr,queryType,serviceId)
                .compose(RxUtil.<ServiceSearchInfo>applySchedulers())
                .subscribe(new OldBaseObserver<ServiceSearchInfo>(searchServiceView) {
                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        searchServiceView.renderNoResult();
                    }

                    @Override
                    public void onNext(ServiceSearchInfo serviceSearchInfo) {
                        searchServiceView.renderRetrurnBack(serviceSearchInfo);
                    }
                });
    }


    public void getHotServiceInfo(String latitude, String longitude){
        hotServiceInfoRepo.getHotServiceInfoList(latitude, longitude)
                .compose(RxUtil.<List<HotServiceInfo>>applySchedulers())
                .subscribe(new OldBaseObserver<List<HotServiceInfo>>(searchServiceView) {
                    @Override
                    public void onNext(List<HotServiceInfo> hotServiceInfos) {
                        searchServiceView.renderHotService(hotServiceInfos);
                    }
                });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
