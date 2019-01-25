package com.homepaas.sls.di.component;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.homepage_3_3_0.CategoryActivity;
import com.homepaas.sls.ui.homepage_3_3_0.CategoryFragment;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/24.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface CategoryComponet {
    void inject(CategoryFragment fragment);
    void inject(CategoryActivity activity);
}
