package com.homepaas.sls.mvp.presenter.pay;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.domain.repository.NewAccountInfoRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.account.WalletBalanceView;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/2/9.
 */
@ActivityScope
public class PayInStoreOrderPresenter implements Presenter {
    @Inject
    public PayInStoreOrderPresenter() {
    }


    private WalletBalanceView walletBalanceView;

    public void setWalletBalanceView(WalletBalanceView walletBalanceView) {
        this.walletBalanceView = walletBalanceView;
    }

    @Inject
    NewAccountInfoRepo response;

    /**
     * 新账户余额信息
     */
    public void getAccountInfo() {
        response.getAccountInfo()
                .compose(RxUtil.<NewAccountInfo>applySchedulers())
                .subscribe(new OldBaseObserver<NewAccountInfo>(walletBalanceView) {
                    @Override
                    public void onNext(NewAccountInfo newAccountInfo) {
                        walletBalanceView.renderAccountInfo(newAccountInfo);
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
