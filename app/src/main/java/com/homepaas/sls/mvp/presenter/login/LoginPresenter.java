package com.homepaas.sls.mvp.presenter.login;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.Account;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.NotNormalDeviceException;
import com.homepaas.sls.domain.interactor.GetLoginInfoInteractor;
import com.homepaas.sls.domain.interactor.LoginInteractor;
import com.homepaas.sls.domain.interactor.QuickLoginInteractor;
import com.homepaas.sls.domain.interactor.SendCaptchaInteractor;
import com.homepaas.sls.domain.param.LoginParam;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.login.LoginView;

import java.util.List;

import javax.inject.Inject;

/**
 * on 2016/1/20 0020
 *
 * @author zhudongjie .
 */
@ActivityScope
public class LoginPresenter implements Presenter {

    private static final String TAG = "LoginPresenter";

    @Inject
    LoginInteractor loginInteractor;

    @Inject
    SendCaptchaInteractor sendCaptchaInteractor;

    @Inject
    GetLoginInfoInteractor getLoginInfoInteractor;

    @Inject
    QuickLoginInteractor quickLoginInteractor;

    private LoginView loginView;

    @Inject
    public LoginPresenter() {
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        loginInteractor.unsubscribe();
        sendCaptchaInteractor.unsubscribe();
        getLoginInfoInteractor.unsubscribe();
        quickLoginInteractor.unsubscribe();
    }

    public void quickLogin(String account, String captcha) {
        loginView.showLoading();
        quickLoginInteractor.setPhone(account);
        quickLoginInteractor.setCaptcha(captcha);
        quickLoginInteractor.execute(new OldBaseObserver<LoginBody>(loginView) {
            @Override
            public void onNext(LoginBody loginBody) {
                loginView.login(loginBody);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t instanceof GetDataException) {
                    GetDataException getDataException = (GetDataException) t;
                    // 1101  是验证码错误或者超时  10 是 手机号错误  1002----验证码错误，请输入正确的验证码
                    if (getDataException.getErrorCode().equals("1101") || getDataException.getErrorCode().equals("10") || getDataException.getErrorCode().equals("1102"))
                        loginView.sendCodeError();
                }
            }
        });

    }

    public void login(String account, String password, String captcha) {
        loginView.showLoading();
        LoginParam param = new LoginParam(account, password);
        param.setCaptcha(captcha);
        loginInteractor.setParam(param);
        loginInteractor.execute(new OldBaseObserver<LoginBody>(loginView) {
            //重写自行处理error信息  方法
            @Override
            public void showError(Throwable e) {
                if (e instanceof NotNormalDeviceException) {
                    loginView.showAuthCodeInput();
                } else {
                    super.showError(e);
                }
            }

            @Override
            public void onNext(LoginBody message) {
                if (message != null)
                    loginView.login(message);
            }
        });
    }

    public void sendAuthCode(String account, int type) {
        loginView.showLoading();
        sendCaptchaInteractor.setPhone(account);
        sendCaptchaInteractor.setType(type);
        sendCaptchaInteractor.execute(new OldBaseObserver<CaptchaBody>(loginView) {
            @Override
            public void onNext(CaptchaBody captchaBody) {
                loginView.coldDown(captchaBody);
            }
        });
    }


    public void getAccountList() {
        getLoginInfoInteractor.execute(new OldBaseObserver<List<Account>>(loginView) {
            @Override
            public void onNext(List<Account> accountList) {
                loginView.popupAccount(accountList);
            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }
        });
    }

}
