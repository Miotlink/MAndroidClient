package com.homepaas.sls.mvp.presenter.city;

import com.homepaas.sls.domain.entity.CityListEntity;
import com.homepaas.sls.domain.repository.CityRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.city.CityView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/7/21.
 */

public class CityPresenter  implements Presenter {
    @Inject
    CityRepo cityRepo;

    @Inject
    public CityPresenter() {
    }

    private CityView cityView;

    public void setCityView(CityView cityView) {
        this.cityView = cityView;
    }

    public void getCityList(){
        cityView.showLoading();
        cityRepo.getCityList()
               .compose(RxUtil.<CityListEntity>applySchedulers())
                .subscribe(new OldBaseObserver<CityListEntity>(cityView) {
                    @Override
                    public void onNext(CityListEntity cityListEntity) {
                        cityView.render(cityListEntity);
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
