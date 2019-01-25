package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.domain.entity.ServiceItemListEntity;
import com.homepaas.sls.domain.repository.QueryServiceRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.view.CategoryView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/24.
 */

public class CategoryPresenter implements Presenter {
    @Inject
    QueryServiceRepo queryServiceRepo;
    private CategoryView categoryView;

    @Inject
    public CategoryPresenter() {
    }

    public void setCategoryView(CategoryView categoryView) {
        this.categoryView = categoryView;
    }

    public void getQueryService(String longitude, String latitude, String serviceId){
        categoryView.showLoading(true);
        queryServiceRepo.getQueryService(longitude,latitude,serviceId)
               .compose(RxUtil.<ServiceItemListEntity>applySchedulers())
                .subscribe(new OldBaseObserver<ServiceItemListEntity>(categoryView) {
                    @Override
                    public void onNext(ServiceItemListEntity serviceItemListEntity) {
                        categoryView.render(serviceItemListEntity.getItems());
                        categoryView.renderTitle(serviceItemListEntity.getTitle());
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
