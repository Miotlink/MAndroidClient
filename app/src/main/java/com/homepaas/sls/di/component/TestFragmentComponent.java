package com.homepaas.sls.di.component;

import com.homepaas.sls.test.TestAFragment;
import com.homepaas.sls.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by mhy on 2017/8/16.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface TestFragmentComponent {
    void inject(TestAFragment testFragment);
}
