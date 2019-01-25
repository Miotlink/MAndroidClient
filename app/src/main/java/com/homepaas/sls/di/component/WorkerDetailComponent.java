package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.CallLogModule;
import com.homepaas.sls.di.module.ShareModule;
import com.homepaas.sls.di.module.WorkerDetailModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.detail.WorkerDetailFragment;
import com.homepaas.sls.ui.detail.WorkerDetailMessageActivity;
import com.homepaas.sls.ui.detail.WorkerMessageFragment;

import dagger.Component;

/**
 * on 2016/1/19 0019
 *
 * @author zhudongjie .
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {ShareModule.class,WorkerDetailModule.class, CallLogModule.class,ActivityModule.class})
public interface WorkerDetailComponent extends ActivityComponent{

  void inject(WorkerDetailFragment fragment);

  void inject(WorkerMessageFragment fragment);


  void inject(WorkerDetailMessageActivity workerDetailMessageActivity);
}
