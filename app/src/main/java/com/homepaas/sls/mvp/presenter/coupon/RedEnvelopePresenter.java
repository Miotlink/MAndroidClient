package com.homepaas.sls.mvp.presenter.coupon;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.CouponContentsInfo;
import com.homepaas.sls.domain.interactor.GetRedEnvelopeInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.coupon.CouponContentsView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/6/22.
 */
@ActivityScope
public class RedEnvelopePresenter implements Presenter {


    GetRedEnvelopeInteractor getRedEnvelopeInteractor;
    CouponContentsView couponContentsView;

    public void setCouponContentsView(CouponContentsView couponContentsView) {
        this.couponContentsView = couponContentsView;
    }

    @Inject
    public RedEnvelopePresenter(GetRedEnvelopeInteractor getRedEnvelopeInteractor) {
        this.getRedEnvelopeInteractor = getRedEnvelopeInteractor;
    }

    public void getRedEnvelopeInfo(String status) {
        couponContentsView.showLoading();
        getRedEnvelopeInteractor.setStatus(status);
        getRedEnvelopeInteractor.execute(new OldBaseObserver<CouponContentsInfo>(couponContentsView) {
            @Override
            public void onNext(CouponContentsInfo couponContentsInfo) {
                couponContentsView.render(couponContentsInfo.getCouponContentsList());
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
        getRedEnvelopeInteractor.unsubscribe();

    }
}
