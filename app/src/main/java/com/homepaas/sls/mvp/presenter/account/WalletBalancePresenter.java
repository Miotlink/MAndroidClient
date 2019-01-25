package com.homepaas.sls.mvp.presenter.account;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.domain.repository.NewAccountInfoRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.account.WalletBalanceView;

import javax.inject.Inject;


/**
 * Created by Administrator on 2016/11/28.
 */
@ActivityScope
public class WalletBalancePresenter implements Presenter {
    private WalletBalanceView walletBalanceView;

    @Inject
    public WalletBalancePresenter() {
    }

    public void setWalletBalanceView(WalletBalanceView walletBalanceView) {
        this.walletBalanceView = walletBalanceView;
    }

    @Inject
    NewAccountInfoRepo response;

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
