package com.homepaas.sls.mvp.presenter.login;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.interactor.RequestResetPasswordInteractor;
import com.homepaas.sls.domain.interactor.SendCaptchaInteractor;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.login.ResetPasswordOneView;

import javax.inject.Inject;

/**
 * on 2016/1/21 0021
 *
 * @author zhudongjie .
 */
@ActivityScope
public class ResetPasswordPresenter implements Presenter {

    ResetPasswordOneView resetPasswordOneView;

    @Inject
    SendCaptchaInteractor sendCaptchaInteractor;

    @Inject
    RequestResetPasswordInteractor requestResetPasswordInteractor;

    @Inject
    public ResetPasswordPresenter() {
    }

    public void setResetPasswordOneView(ResetPasswordOneView resetPasswordOneView) {
        this.resetPasswordOneView = resetPasswordOneView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        sendCaptchaInteractor.unsubscribe();
        requestResetPasswordInteractor.unsubscribe();
    }

    @SuppressWarnings("unchecked")
    public void sendAuthCode(String phone) {
        resetPasswordOneView.showLoading();
        sendCaptchaInteractor.setPhone(phone);
        sendCaptchaInteractor.setType(Constant.CAPTCHA_PASSWORD);
        sendCaptchaInteractor.execute(new OldBaseObserver<CaptchaBody>(resetPasswordOneView) {
            @Override
            public void onNext(CaptchaBody captchaBody) {
                resetPasswordOneView.coldDown(captchaBody);
            }
        });

    }

    @SuppressWarnings("unchecked")
    public void requestResetPassword(String account, String authCode) {
        resetPasswordOneView.showLoading();
        requestResetPasswordInteractor.setPhone(account);
        requestResetPasswordInteractor.setCaptcha(authCode);
        requestResetPasswordInteractor.execute(new OldBaseObserver(resetPasswordOneView) {
            @Override
            public void onNext(Object o) {
                resetPasswordOneView.showNewPasswordView();
            }
        });
    }
}
