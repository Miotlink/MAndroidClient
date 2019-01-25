package com.homepaas.sls.mvp.presenter.personalcenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.AdsInfo;
import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.interactor.AdsInfoInteractor;
import com.homepaas.sls.domain.interactor.BannersInfoInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/12/20.
 */
@ActivityScope
public class BannerPresenter implements Presenter {
    private BannersInfoInteractor bannersInfoInteractor;
    private AdsInfoInteractor adsInfoInteractor;

    @Inject
    public BannerPresenter(BannersInfoInteractor bannersInfoInteractor, AdsInfoInteractor adsInfoInteractor) {
        this.bannersInfoInteractor = bannersInfoInteractor;
        this.adsInfoInteractor = adsInfoInteractor;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        bannersInfoInteractor.unsubscribe();
        adsInfoInteractor.unsubscribe();
    }
}
