package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.EditCommentModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.comment.AddCommentActivity;

import dagger.Component;

/**
 * on 2016/6/24 0024
 *
 * @author zhudongjie
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = EditCommentModule.class)
public interface AddCommentComponent {

    void inject(AddCommentActivity activity);
}
