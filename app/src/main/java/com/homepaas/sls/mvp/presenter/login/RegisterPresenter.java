package com.homepaas.sls.mvp.presenter.login;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.entity.RegisterBody;
import com.homepaas.sls.domain.interactor.RegisterInteractor;
import com.homepaas.sls.domain.interactor.SendCaptchaInteractor;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.domain.param.RegisterParam;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.login.RegisterView;

import javax.inject.Inject;

/**
 * on 2016/1/21 0021
 *
 * @author zhudongjie .
 */
@ActivityScope
public class RegisterPresenter implements Presenter {

    @Inject
    SendCaptchaInteractor sendCaptchaInteractor;

    @Inject
    RegisterInteractor registerInteractor;

    private RegisterView registerView;

    @Inject
    public RegisterPresenter() {
    }

    public void setRegisterView(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.sendCaptchaInteractor.unsubscribe();
        this.registerInteractor.unsubscribe();
    }


    public void sendAuthCode(String phone) {
        registerView.showLoading();
        sendCaptchaInteractor.setPhone(phone);
        sendCaptchaInteractor.setType(Constant.CAPTCHA_REGISTER);
        sendCaptchaInteractor.execute(new OldBaseObserver<CaptchaBody>(registerView) {
            @Override
            public void onNext(CaptchaBody captchaBody) {
                registerView.coldDown(captchaBody);
            }
        });
    }

    public void Register(String account, String password, String authCode,String invitationCode) {
        registerView.showLoading();
        registerInteractor.setRegisterParam(new RegisterParam(account, password, authCode, Constant.REGISTER_TYPE_LOGIN,invitationCode));
        registerInteractor.execute(new OldBaseObserver<RegisterBody>(registerView) {
            @Override
            public void onNext(RegisterBody response) {
                registerView.onRegisterSucceed(response);
            }
        });
    }
}
