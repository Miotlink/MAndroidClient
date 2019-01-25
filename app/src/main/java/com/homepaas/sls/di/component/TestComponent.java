package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.TestModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.test.TestActivity;

import dagger.Component;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules ={TestModule.class} )
public interface TestComponent {
    void inject(TestActivity activity);
}
