package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ModifyPasswordModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.login.ModifyPasswordActivity;

import dagger.Component;

/**
 * on 2016/2/18 0018
 *
 * @author zhudongjie .
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ModifyPasswordModule.class})
public interface ModifyPasswordComponent {

    void inject(ModifyPasswordActivity activity);
}
