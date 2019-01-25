package com.homepaas.sls.mvp.view.login;

import com.homepaas.sls.domain.entity.Account;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2016/1/21 0021
 *
 * @author zhudongjie .
 */
public interface LoginView extends BaseView {

    void popupAccount(List<Account> accountList);


    void showAuthCodeInput();

    void login(LoginBody loginBody);

    void coldDown(CaptchaBody captchaBody);

    void sendCodeError();
}
