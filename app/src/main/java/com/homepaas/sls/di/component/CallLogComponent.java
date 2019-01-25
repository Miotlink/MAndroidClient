package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.BusinessDetailModule;
import com.homepaas.sls.di.module.CallLogModule;
import com.homepaas.sls.di.module.ShareModule;
import com.homepaas.sls.di.module.WorkerDetailModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.personalcenter.CallLogsActivity;

import dagger.Component;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ShareModule.class,CallLogModule.class, ActivityModule.class, WorkerDetailModule.class, BusinessDetailModule.class})
public interface CallLogComponent extends ActivityComponent {

    void inject(CallLogsActivity activity);
}
