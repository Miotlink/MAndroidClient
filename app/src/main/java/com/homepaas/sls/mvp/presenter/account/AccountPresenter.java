package com.homepaas.sls.mvp.presenter.account;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.AccountDetail;
import com.homepaas.sls.domain.entity.AccountInfo;
import com.homepaas.sls.domain.interactor.GetAccountDetailsInteractor;
import com.homepaas.sls.domain.interactor.GetAccountInfoInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.account.AccountBalanceView;

import java.util.List;

import javax.inject.Inject;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
@ActivityScope
public class AccountPresenter implements Presenter {

    private static final String TAG = "AccountPresenter";

    private AccountBalanceView accountBalanceView;

    @Inject
    GetAccountDetailsInteractor detailsInteractor;

    @Inject
    GetAccountInfoInteractor infoInteractor;

    @Inject
    public AccountPresenter() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        detailsInteractor.unsubscribe();
        infoInteractor.unsubscribe();
    }

    public void setAccountBalanceView(AccountBalanceView accountBalanceView) {
        this.accountBalanceView = accountBalanceView;
    }

    public void get() {
        infoInteractor.execute(new OldBaseObserver<AccountInfo>(accountBalanceView) {
            @Override
            public void onNext(AccountInfo accountInfo) {
                accountBalanceView.renderBalance(accountInfo);
            }
        });

        detailsInteractor.execute(new OldBaseObserver<List<AccountDetail>>(accountBalanceView) {
            @Override
            public void onNext(List<AccountDetail> accountDetails) {
                accountBalanceView.renderDetail(accountDetails);
            }
        });
    }

}
