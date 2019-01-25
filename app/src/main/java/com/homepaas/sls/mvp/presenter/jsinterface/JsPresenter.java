package com.homepaas.sls.mvp.presenter.jsinterface;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.GetTokenInteractor;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.jsinterface.JsInterfaceView;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/12.
 */
@ActivityScope
public class JsPresenter implements Presenter{

    @Inject
    GetTokenInteractor getTokenInteractor;
    JsInterfaceView jsInterfaceView;

    @Inject
    public JsPresenter(){}

    public JsPresenter(JsInterfaceView jsInterfaceView) {
        this.jsInterfaceView = jsInterfaceView;
    }

    public void setJsInterfaceView(JsInterfaceView jsInterfaceView) {
        this.jsInterfaceView = jsInterfaceView;
    }

    public void getToken(){
        getTokenInteractor.execute(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                jsInterfaceView.onToken(s);
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
        getTokenInteractor.unsubscribe();
    }
}
