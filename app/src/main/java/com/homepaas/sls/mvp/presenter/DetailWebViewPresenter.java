package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.interactor.GetPersonalInfoInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.view.DetailServiceWebView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/31.
 */

public class DetailWebViewPresenter implements Presenter {
    @Inject
    GetPersonalInfoInteractor getPersonalInfoInteractor;
    private DetailServiceWebView detailServiceWebView;

    @Inject
    public DetailWebViewPresenter() {
    }

    public void setDetailServiceWebView(DetailServiceWebView detailServiceWebView) {
        this.detailServiceWebView = detailServiceWebView;
    }
    public void loadPersonalInfo() {
        getPersonalInfoInteractor.execute(new OldBaseObserver<PersonalInfo>(detailServiceWebView) {
            @Override
            public void onNext(PersonalInfo personalInfo) {
                //在登录的情况下获取个人信息，并保存telphonenumber,不需要返回给上层，已经在repo层保存
                detailServiceWebView.pushTelephone(personalInfo.getPhoneNumber());
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
        getPersonalInfoInteractor.unsubscribe();
    }
}
