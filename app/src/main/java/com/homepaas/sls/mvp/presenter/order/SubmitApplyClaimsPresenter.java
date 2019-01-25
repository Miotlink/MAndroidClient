package com.homepaas.sls.mvp.presenter.order;

import com.homepaas.sls.domain.repository.SubmitApplyClaimsRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.SubmitApplyClaimsView;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/7/6.
 */

public class SubmitApplyClaimsPresenter implements Presenter {

    private SubmitApplyClaimsView submitApplyClaimsView;
    public void setSubmitApplyClaimsView(SubmitApplyClaimsView submitApplyClaimsView){
        this.submitApplyClaimsView=submitApplyClaimsView;
    }
    @Inject
    SubmitApplyClaimsRepo submitApplyClaimsRepo;

    @Inject
    public SubmitApplyClaimsPresenter() {
    }

    public void submitApplyClaims(String orderId, String reasonType, String workerLaterTime, String reason) {
        submitApplyClaimsView.showLoading();
        submitApplyClaimsRepo.submitApplyClaims(orderId, reasonType, workerLaterTime, reason)
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new OldBaseObserver<String>(submitApplyClaimsView) {
                    @Override
                    public void onNext(String s) {
                        submitApplyClaimsView.renderSuccess();
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
