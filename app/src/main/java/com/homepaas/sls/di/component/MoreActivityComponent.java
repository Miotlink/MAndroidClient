package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.PersonalInfoModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.personalcenter.more.MoreActivity;

import dagger.Component;

/**
 * on 2016/5/24 0024
 *
 * @author zhudongjie
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class ,modules = {PersonalInfoModule.class})
public interface MoreActivityComponent {

    void inject(MoreActivity activity);
}
