package com.homepaas.sls.mvp.presenter.SearchService;

import com.homepaas.sls.domain.entity.ServiceItemListEntity;
import com.homepaas.sls.domain.repository.HotSearchServiceRepo;
import com.homepaas.sls.domain.repository.SearchTopicRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.SearchService.CommonSearchServiceView;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/3/27.
 * 3.3.0搜索页面的presenter
 */

public class CommonSearchServicePresenter implements Presenter {
    private CommonSearchServiceView commonSearchServiceView;

    public void setCommonSearchServiceView(CommonSearchServiceView commonSearchServiceView) {
        this.commonSearchServiceView = commonSearchServiceView;
    }

    @Inject
    public CommonSearchServicePresenter() {
    }

    @Inject
    HotSearchServiceRepo hotSearchServiceRepo;
    @Inject
    SearchTopicRepo topicRepo;

    /**
     * 获取热门搜索结果
     *
     * @param longitude
     * @param latitude
     */
    public void getHotSearchService(String longitude, String latitude) {
        hotSearchServiceRepo.getQueryService(longitude, latitude)
                .compose(RxUtil.<ServiceItemListEntity>applySchedulers())
                .subscribe(new OldBaseObserver<ServiceItemListEntity>(commonSearchServiceView) {
                    @Override
                    public void onNext(ServiceItemListEntity serviceItemListEntity) {
                        commonSearchServiceView.renderHotService(serviceItemListEntity);
                    }
                });
    }

    /**
     * 输入搜索字后的结果
     *
     * @param longitude
     * @param latitude
     * @param queryStr
     */
    public void getResultService(String longitude, String latitude, String queryStr) {
        topicRepo.getQueryService(longitude, latitude, queryStr)
                .compose(RxUtil.<ServiceItemListEntity>applySchedulers())
                .subscribe(new OldBaseObserver<ServiceItemListEntity>(commonSearchServiceView) {
                    @Override
                    public void onNext(ServiceItemListEntity serviceItemListEntity) {
                        commonSearchServiceView.renderResultService(serviceItemListEntity);
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
