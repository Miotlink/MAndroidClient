package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.LoginModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.login.AccountLoginFragment;
import com.homepaas.sls.ui.login.FastLoginActivity;
import com.homepaas.sls.ui.login.LoginActivity;
import com.homepaas.sls.ui.login.LoginDialogFragment;
import com.homepaas.sls.ui.login.QuickLoginFragment;
import com.homepaas.sls.ui.order.chooseservice.JsSelectServiceItemFragment;
import com.homepaas.sls.ui.order.chooseservice.SelectServiceItemFragment;
import com.homepaas.sls.ui.order.chooseservice.TitleServiceItemFragment;

import dagger.Component;

/**
 * on 2016/1/21 0021
 *
 * @author zhudongjie .
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {LoginModule.class})
public interface LoginComponent {

    void inject(LoginActivity activity);
    void inject(FastLoginActivity activity);

    void inject(LoginDialogFragment fragment);

    void inject(SelectServiceItemFragment fragment);
    void inject(TitleServiceItemFragment fragment);
    void inject(JsSelectServiceItemFragment fragment);
    void inject(QuickLoginFragment fragment);
    void inject(AccountLoginFragment fragment);
}
