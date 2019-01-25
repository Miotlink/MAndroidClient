package com.homepaas.sls.mvp.presenter.account;

import com.homepaas.sls.domain.entity.RechargeInfoResponse;
import com.homepaas.sls.domain.repository.RechargeInfoRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.account.RechargeInfoView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/12/7.
 */

public class RechargeInfoPresent implements Presenter {

    private RechargeInfoView rechargeInfoView;
    public void setRechargeInfoView(RechargeInfoView rechargeInfoView){
        this.rechargeInfoView=rechargeInfoView;
    }
    @Inject
    public RechargeInfoPresent() {
    }

    @Inject
    RechargeInfoRepo rechargeInfoRepo;
    public void getRechargeInfo(String rechargeCode){
        rechargeInfoRepo.getRechargeInfo(rechargeCode)
               .compose(RxUtil.<RechargeInfoResponse>applySchedulers())
                .subscribe(new OldBaseObserver<RechargeInfoResponse>(rechargeInfoView) {
                    @Override
                    public void onNext(RechargeInfoResponse rechargeInfoEntry) {
                        rechargeInfoView.renderRechargeInfo(rechargeInfoEntry);
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
