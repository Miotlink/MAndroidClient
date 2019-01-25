package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.RegisterModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.login.RegisterActivity;

import dagger.Component;

/**
 * on 2016/1/21 0021
 *
 * @author zhudongjie .
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {RegisterModule.class, ActivityModule.class})
public interface RegisterComponent {

    void inject(RegisterActivity activity);

}
