package com.homepaas.sls.mvp.presenter.coupon;

import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.interactor.AddShareRecordInteractor;
import com.homepaas.sls.domain.interactor.GetShareInfoInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.coupon.AddShareCouponView;
import com.homepaas.sls.mvp.view.coupon.GetShareCouponView;

import javax.inject.Inject;

/**
 * Created by Sherily on 2016/7/4.
 */
public class ShareCoponPresenter implements Presenter {

    private GetShareInfoInteractor getShareInfoInteractor;
    private AddShareRecordInteractor addShareRecordInteractor;
    private GetShareCouponView getShareCouponView;
    private AddShareCouponView addShareCouponView;


    public void setGetShareCouponView(GetShareCouponView getShareCouponView) {
        this.getShareCouponView = getShareCouponView;
    }

    public void setAddShareCouponView(AddShareCouponView addShareCouponView) {
        this.addShareCouponView = addShareCouponView;
    }

    @Inject
    public ShareCoponPresenter(GetShareInfoInteractor getShareInfoInteractor, AddShareRecordInteractor addShareRecordInteractor) {
        this.getShareInfoInteractor = getShareInfoInteractor;
        this.addShareRecordInteractor = addShareRecordInteractor;
    }

    public void shareCoupon() {
        getShareCouponView.showLoading();
//        getShareInfoInteractor.setId(id);//6:我的优惠券分享
        getShareInfoInteractor.execute(new OldBaseObserver<ShareInfo>(getShareCouponView) {
            @Override
            public void onNext(ShareInfo shareInfo) {
                getShareCouponView.share(shareInfo);
            }
        });
    }

    public void addShareCouponRecord() {
        addShareCouponView.showLoading();
//        addShareRecordInteractor.setId(id);
        addShareRecordInteractor.execute(new OldBaseObserver<String>(addShareCouponView) {
            @Override
            public void onNext(String s) {
                addShareCouponView.addShareRecod(s);
            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
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
        getShareInfoInteractor.unsubscribe();
        addShareRecordInteractor.unsubscribe();
    }
}
