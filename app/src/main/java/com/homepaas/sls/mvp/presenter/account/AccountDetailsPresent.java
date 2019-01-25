package com.homepaas.sls.mvp.presenter.account;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.AccountDetailItemEntry;
import com.homepaas.sls.domain.repository.AccountDetailsRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.account.AccountDetailsView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/6.
 */
@ActivityScope
public class AccountDetailsPresent implements Presenter {

    @Inject
    public AccountDetailsPresent() {
    }

    @Inject
    AccountDetailsRepo repository;

    private AccountDetailsView accountDetailsView;

    public void setAccountDetailsView(AccountDetailsView accountDetailsView) {
        this.accountDetailsView = accountDetailsView;
    }


    public void getAccountDetails(int id) {
        repository.getAccountDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new OldBaseObserver<AccountDetailItemEntry>(accountDetailsView) {
                    @Override
                    public void onNext(AccountDetailItemEntry accountDetailItemEntry) {
                        accountDetailsView.renderAccountDetails(accountDetailItemEntry);
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
