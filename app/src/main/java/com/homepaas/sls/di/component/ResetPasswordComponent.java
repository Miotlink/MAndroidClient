package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.ResetPasswordModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.login.ResetPasswordStepTwoFragment;
import com.homepaas.sls.ui.login.ResetPasswordStepOneFragment;

import dagger.Component;

/**
 * on 2016/1/20 0020
 *
 * @author zhudongjie .
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ResetPasswordModule.class,ActivityModule.class})
public interface ResetPasswordComponent extends ActivityComponent {

    void inject(ResetPasswordStepOneFragment fragment);

    void inject(ResetPasswordStepTwoFragment fragment);
}
