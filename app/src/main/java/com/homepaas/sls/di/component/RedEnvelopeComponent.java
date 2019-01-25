package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.RedEnvelopeModule;
import com.homepaas.sls.di.module.ShareModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.coupon.CouponActivity;

import dagger.Component;

/**
 * Created by Administrator on 2016/6/22.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {RedEnvelopeModule.class,ShareModule.class})
public interface RedEnvelopeComponent {
    void inject(CouponActivity couponActivity);
}
