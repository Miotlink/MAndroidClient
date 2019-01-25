package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.CommentModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.comment.MyCommentActivity;

import dagger.Component;

/**
 * on 2016/6/20 0020
 *
 * @author zhudongjie
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {CommentModule.class})
public interface MyCommentComponent {

    void inject(MyCommentActivity activity);
}
