package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.BannerModule;
import com.homepaas.sls.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Administrator on 2016/12/20.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {BannerModule.class})
public interface BannerComponet{
}
