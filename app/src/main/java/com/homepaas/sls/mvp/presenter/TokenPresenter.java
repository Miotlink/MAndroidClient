package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.interactor.GetTokenInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.view.TokenView;

import javax.inject.Inject;

/**
 * Created by CJJ on 2016/10/14.
 */
@ActivityScope
public class TokenPresenter {

    @Inject
    public TokenPresenter() {
    }

    @Inject
    GetTokenInteractor getTokenInteractor;
    TokenView tokenView;

    public void getToken(final TokenView tokenView) {
        this.tokenView = tokenView;
        getTokenInteractor.execute(new OldBaseObserver<String>(tokenView) {
            @Override
            public void showError(Throwable t) {
//                super.showError(t);
                if (t instanceof AuthException)
                    tokenView.showLogin();
            }

            @Override
            public void onNext(String s) {
                tokenView.onToken(s);
            }
        });
    }
}
