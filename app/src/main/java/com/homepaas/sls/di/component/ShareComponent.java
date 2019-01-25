package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ShareModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.coupon.ShareCouponActivity;

import dagger.Component;

/**
 * Created by Administrator on 2016/7/12.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {ShareModule.class})
public interface ShareComponent {

    void inject(ShareCouponActivity activity);
}
