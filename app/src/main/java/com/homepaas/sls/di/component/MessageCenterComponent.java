package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.MessageModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.personalcenter.MessageCenterActivity;

import dagger.Component;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {MessageModule.class, ActivityModule.class})
public interface MessageCenterComponent extends ActivityComponent {

    void inject(MessageCenterActivity activity);
}
