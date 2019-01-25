package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.PersonalInfoModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.homepage_3_3_0.CityActivity;

import dagger.Component;

/**
 * Created by Sherily on 2017/7/21.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = { PersonalInfoModule.class})
public interface CityComponent {
    void inject(CityActivity cityActivity);
}
