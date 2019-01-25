package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.view.MoreActivityView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2016/12/23.
 */
@ActivityScope
public class MoreActivityPresenter implements Presenter {
    private Interactor logoutInteractor;
    private MoreActivityView moreActivityView;

    @Inject
    public MoreActivityPresenter(@Named("LogoutInteractor")Interactor logoutInteractor) {
        this.logoutInteractor = logoutInteractor;
    }

    public void setMoreActivityView(MoreActivityView moreActivityView) {
        this.moreActivityView = moreActivityView;
    }
    public void logout() {
        logoutInteractor.execute(new OldBaseObserver<Boolean>(moreActivityView) {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    moreActivityView.logout();
                } else {
                    moreActivityView.showMessage("登出失败");
                    moreActivityView.logout();
                }
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
        logoutInteractor.unsubscribe();

    }
}
