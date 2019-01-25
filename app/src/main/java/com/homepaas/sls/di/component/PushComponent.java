package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ApplicationModule;
import com.homepaas.sls.di.module.PushModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.pushservice.JPushReceiver;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/7/10.
 */
@Singleton
@Component(modules = {PushModule.class})
public interface PushComponent {

    void inject(JPushReceiver jPushReceiver);
}
