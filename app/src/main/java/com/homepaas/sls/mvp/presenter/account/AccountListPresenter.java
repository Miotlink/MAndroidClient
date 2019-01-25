package com.homepaas.sls.mvp.presenter.account;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.AccountListEntity;
import com.homepaas.sls.domain.repository.SettlementListRepository;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.account.AccountListView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/29.
 */
@ActivityScope
public class AccountListPresenter implements Presenter {
    private int currentIndex = 1;
    private static final int DEFAULT_SIZE = 10;

    private AccountListView accountListView;

    public void setAccountListView(AccountListView accountListView) {
        this.accountListView = accountListView;
    }

    @Inject
    public AccountListPresenter() {
    }

    @Inject
    SettlementListRepository response;

    public void getSettleList(String isMinus) {
        currentIndex = 1;
        response.getSettlementList(isMinus, currentIndex, DEFAULT_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OldBaseObserver<AccountListEntity>(accountListView) {
                    @Override
                    public void onNext(AccountListEntity accountListEntity) {
                        accountListView.renderSettlementList(accountListEntity);
                    }
                });
    }

    public void getMoreSettlementList(String isMinus) {
        response.getSettlementList(isMinus, ++currentIndex, DEFAULT_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OldBaseObserver<AccountListEntity>(accountListView) {

                    @Override
                    public void showError(Throwable t) {
//                        super.showError(t);
                    }

                    @Override
                    public void onNext(AccountListEntity accountListEntity) {
                        accountListView.renderSettlenmentMoreList(accountListEntity);
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
