package com.homepaas.sls.mvp.presenter.share;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.interactor.GetShareInfoInteractor;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.view.share.ShareView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/7/12.
 */
@ActivityScope
public class SharePresenter {

    @Inject
    GetShareInfoInteractor getShareInfoInteractor;
    ShareView shareView;

    public void setShareView(ShareView shareView) {
        this.shareView = shareView;
    }

    @Inject
    public SharePresenter() {

    }

    public void getShareCoupon(){
        getShareInfoInteractor.setId(Constant.SHARE_TYPE_MY_COUPON);
        getShareInfoInteractor.execute(new OldBaseObserver<ShareInfo>(shareView) {
            @Override
            public void onNext(ShareInfo shareInfo) {
                shareView.onShareResult(shareInfo);
            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }
        });
    }
}
