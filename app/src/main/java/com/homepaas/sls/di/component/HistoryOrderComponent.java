package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.HistoryModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.order.history.HistoryFragment;

import dagger.Component;

/**
 * Created by mhy on 2017/8/28.
 */
@ActivityScope()
@Component(dependencies = ApplicationComponent.class,  modules={HistoryModule.class})
public interface HistoryOrderComponent  {
    void inject(HistoryFragment fragment);

}
