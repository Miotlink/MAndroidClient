package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.GetDescriptInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.view.GetDescriptionView;

import javax.inject.Inject;

/**
 * Created by CJJ on 2016/7/13.
 */
@ActivityScope
public class DescriptionPresenter implements Presenter {
    @Inject
    public DescriptionPresenter() {
    }

    GetDescriptionView getDescriptionView;

    public void setGetDescriptionView(GetDescriptionView getDescriptionView) {
        this.getDescriptionView = getDescriptionView;
    }

    @Inject
    GetDescriptInteractor getDescriptInteractor;


    public void getWarmDescription(){
        getDescriptInteractor.setCode("Code001");
        getDescriptInteractor.execute(new OldBaseObserver<String>(getDescriptionView) {
            @Override
            public void onNext(String s) {
                getDescriptionView.renderDescription(s);
            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }
        });
    }

    public void getServiceDescription() {
        getDescriptInteractor.setCode("Code002");
        getDescriptInteractor.execute(new OldBaseObserver<String>(getDescriptionView) {

            @Override
            public void onNext(String s) {
                getDescriptionView.renderDescription(s);
            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
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
        getDescriptInteractor.unsubscribe();
    }
}
