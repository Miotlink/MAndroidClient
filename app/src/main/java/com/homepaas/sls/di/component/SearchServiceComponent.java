package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.LifeServiceModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.search.SearchActivity;

import dagger.Component;

/**
 * on 2016/2/14 0014
 *
 * @author zhudongjie .
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {LifeServiceModule.class, ActivityModule.class})
public interface SearchServiceComponent {

    void inject(SearchActivity activity);
}
