package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.FeedbackModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.personalcenter.more.FeedbackActivity;

import dagger.Component;

/**
 * on 2016/1/23 0023
 *
 * @author zhudongjie .
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = FeedbackModule.class)
public interface FeedbackComponent {

    void inject(FeedbackActivity activity);
}
