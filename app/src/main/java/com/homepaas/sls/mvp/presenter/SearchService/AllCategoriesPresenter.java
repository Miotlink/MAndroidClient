package com.homepaas.sls.mvp.presenter.SearchService;

import com.homepaas.sls.domain.entity.ServiceItemListEntity;
import com.homepaas.sls.domain.repository.AllServiceRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.SearchService.AllCategoriesView;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/3/23.
 */

public class AllCategoriesPresenter implements Presenter {
    @Inject
    public AllCategoriesPresenter() {
    }

    private AllCategoriesView allCategoriesView;
    public void setAllCategoriesView(AllCategoriesView allCategoriesView){
        this.allCategoriesView=allCategoriesView;
    }

    @Inject
    AllServiceRepo allServiceRepo;

    public void getAllService(String longitude,String latitude){
        allCategoriesView.showLoading(true);
        allServiceRepo.getAllService(longitude,latitude)
                .compose(RxUtil.<ServiceItemListEntity>applySchedulers())
                .subscribe(new OldBaseObserver<ServiceItemListEntity>(allCategoriesView) {
                    @Override
                    public void onNext(ServiceItemListEntity serviceItemListEntity) {
                        allCategoriesView.render(serviceItemListEntity);
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
