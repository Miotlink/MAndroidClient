package com.homepaas.sls.mvp.presenter.SearchService;

import com.homepaas.sls.domain.entity.HotServiceInfo;
import com.homepaas.sls.domain.repository.HotServiceInfoRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.SearchService.HomeHotSeviceView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JWC on 2016/12/23.
 */

public class HomeHotServicePresenter implements Presenter {

    @Inject
    public HomeHotServicePresenter() {
    }
    @Inject
    HotServiceInfoRepo hotServiceInfoRepo;


    private HomeHotSeviceView homeHotSeviceView;
    public void setHomeHotSeviceView(HomeHotSeviceView homeHotSeviceView){
        this.homeHotSeviceView=homeHotSeviceView;
    }

    /**
     * 热门服务标签列表
     * @param latitude
     * @param longitude
     */
    public void getHotServiceInfo(String latitude, String longitude){
        hotServiceInfoRepo.getHotServiceInfoList(latitude, longitude)
                .compose(RxUtil.<List<HotServiceInfo>>applySchedulers())
                .subscribe(new OldBaseObserver<List<HotServiceInfo>>(homeHotSeviceView) {
                    @Override
                    public void onNext(List<HotServiceInfo> hotServiceInfos) {
                        homeHotSeviceView.renderHotService(hotServiceInfos);
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
