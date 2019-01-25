package com.homepaas.sls.mvp.view.login;

import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2016/1/21 0021
 *
 * @author zhudongjie .
 */
public interface ResetPasswordOneView extends BaseView {

    void showNewPasswordView();

    void coldDown(CaptchaBody captchaBody);
}
