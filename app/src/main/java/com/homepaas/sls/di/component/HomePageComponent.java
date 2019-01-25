package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.BannerModule;
import com.homepaas.sls.di.module.FirstOrderInfoModule;
import com.homepaas.sls.di.module.MessageModule;
import com.homepaas.sls.di.module.PersonalInfoModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.homepage_3_3_0.HomePageFragment;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/22.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,  modules={MessageModule.class, BannerModule.class,FirstOrderInfoModule.class,PersonalInfoModule.class})
public interface HomePageComponent {
    void inject(HomePageFragment fragment);
}
