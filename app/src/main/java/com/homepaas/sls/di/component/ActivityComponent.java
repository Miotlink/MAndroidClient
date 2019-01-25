package com.homepaas.sls.di.component;

import android.app.Activity;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Administrator on 2015/12/22.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();
}
