package com.homepaas.sls.mvp.presenter.account;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.AliPayRechargeInteractor;
import com.homepaas.sls.domain.interactor.WeChatPayRechargeInteractor;
import com.homepaas.sls.domain.param.RechargePaySignParam;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.account.RechargeView;

import javax.inject.Inject;

/**
 * on 2016/6/28 0028
 *
 * @author zhudongjie
 */
@ActivityScope
public class RechargePresenter implements Presenter{

    private RechargeView view;

    private AliPayRechargeInteractor aliPayRechargeInteractor;

    private WeChatPayRechargeInteractor weChatPayRechargeInteractor;

    @Inject
    public RechargePresenter(AliPayRechargeInteractor aliPayRechargeInteractor, WeChatPayRechargeInteractor weChatPayRechargeInteractor) {
        this.aliPayRechargeInteractor = aliPayRechargeInteractor;
        this.weChatPayRechargeInteractor = weChatPayRechargeInteractor;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        aliPayRechargeInteractor.unsubscribe();
    }

    public void payForAlipay(String total,String need,String activity){
        aliPayRechargeInteractor.setParam(new RechargePaySignParam(need,total,activity));
        aliPayRechargeInteractor.execute(new OldBaseObserver<String>(view) {
            @Override
            public void onNext(String s) {

            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }
        });
    }

    private void payAlipay(){

    }


    public void setView(RechargeView view) {
        this.view = view;
    }
}
