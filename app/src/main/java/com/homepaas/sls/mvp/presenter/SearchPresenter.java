package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.domain.entity.LifeService;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.interactor.SearchServiceListInteractor;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.domain.param.SearchParameter;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.model.mapper.LifeServiceModelMapper;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.view.SearchView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * on 2016/2/14 0014
 *
 * @author zhudongjie .
 */
public class SearchPresenter implements Presenter {

    private SearchView searchView;

    private List<String> serviceModelString;

    private Interactor interactor;

    private Interactor suggestServiceInteractor;

    private LifeServiceModelMapper lifeServiceModelMapper;

    private SearchServiceListInteractor serviceListInteractor;

    private ServiceInfoModelMapper serviceInfoModelMapper;

    private int selectedPosition = -1;

    @Inject
    public SearchPresenter(@Named("GetHotServiceListInteractor") Interactor interactor, @Named("GetLifeServiceListInteractor") Interactor suggestServiceInteractor, LifeServiceModelMapper lifeServiceModelMapper, SearchServiceListInteractor serviceListInteractor, ServiceInfoModelMapper serviceInfoModelMapper) {
        this.interactor = interactor;
        this.suggestServiceInteractor = suggestServiceInteractor;
        this.lifeServiceModelMapper = lifeServiceModelMapper;
        this.serviceListInteractor = serviceListInteractor;
        this.serviceInfoModelMapper = serviceInfoModelMapper;
    }

    @Override
    public void resume() {
        loadHotServices();
        getSuggestedServices();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        serviceListInteractor.unsubscribe();
        suggestServiceInteractor.unsubscribe();
        interactor.unsubscribe();
    }

    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }

    public void loadHotServices() {
        searchView.showLoading();
        interactor.execute(new OldBaseObserver<List<LifeService>>(searchView) {
            @Override
            public void onNext(List<LifeService> lifeService) {
                serviceModelString = lifeServiceModelMapper.transformToStringList(lifeService);
                SearchPresenter.this.serviceModelString = serviceModelString;
                searchView.render(serviceModelString);
            }
        });
    }

    public void getSuggestedServices() {
        suggestServiceInteractor.execute(new OldBaseObserver<List<LifeService>>(searchView) {
            @Override
            public void onNext(List<LifeService> lifeService) {

                List<String> list = lifeServiceModelMapper.transformToItemStringList(lifeService);
                searchView.prepareSuggestion(list);

            }
        });
    }


    public void searchByText(String queryContent, double latitude, double longitude, String serviceType) {
        searchView.showLoading();
        serviceListInteractor.setSearchParameter(new SearchParameter(String.valueOf(latitude), String.valueOf(longitude), serviceType, queryContent, Constant.QUERY_MODE_TEXT, ""));
        serviceListInteractor.execute(new OldBaseObserver<ServiceSearchInfo>(searchView) {

            @Override
            public void onNext(ServiceSearchInfo serviceInfo) {
                searchView.showSearchResult(serviceInfo);
            }
        });
    }


    public String selectService(int position) {
        return serviceModelString.get(position);
    }
}
