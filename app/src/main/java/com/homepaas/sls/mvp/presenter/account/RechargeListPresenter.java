package com.homepaas.sls.mvp.presenter.account;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.RechargeListExEntity;
import com.homepaas.sls.domain.interactor.GetRechargesInteractor;
import com.homepaas.sls.domain.repository.RechargeListExRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.account.RechargeListView;

import javax.inject.Inject;

/**
 * on 2016/6/22 0022
 *
 * @author zhudongjie
 */
@ActivityScope
public class RechargeListPresenter implements Presenter {

    private GetRechargesInteractor getRechargesInteractor;

    private RechargeListView view;

    @Inject
    public RechargeListPresenter(GetRechargesInteractor getRechargesInteractor) {
        this.getRechargesInteractor = getRechargesInteractor;
    }

    @Inject
    RechargeListExRepo rechargeListExRepo;

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getRechargesInteractor.unsubscribe();
    }


    public void obtainRecharges() {
        rechargeListExRepo.getRechargeListEx()
               .compose(RxUtil.<RechargeListExEntity>applySchedulers())
                .subscribe(new OldBaseObserver<RechargeListExEntity>(view) {
                    @Override
                    public void onNext(RechargeListExEntity rechargeListExEntity) {
                        view.render(rechargeListExEntity);
                    }
                });
    }

    public void setView(RechargeListView view) {
        this.view = view;
    }
}
