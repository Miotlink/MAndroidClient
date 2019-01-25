package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.CouponContentsModule;
import com.homepaas.sls.di.module.ShareModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.coupon.CouponActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.NewNoUsedRedPacketFragment;
import com.homepaas.sls.ui.redpacket.newRedpacket.NewOutofdateRedPacketFragment;
import com.homepaas.sls.ui.redpacket.newRedpacket.NewRedPacketActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.NewUsedRedPacketFragment;
import com.homepaas.sls.ui.redpacket.newRedpacket.UseNewRedPacketActivity;

import dagger.Component;

/**
 * Created by Administrator on 2016/6/23.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {CouponContentsModule.class,ShareModule.class})
public interface CouponContentsComponent {
    void inject(CouponActivity couponActivity);
    void inject(NewRedPacketActivity newRedPacketActivity);
    void inject(NewNoUsedRedPacketFragment noUsedRedPacketFragment);
    void inject(NewUsedRedPacketFragment usedRedPacketFragment);
    void inject(NewOutofdateRedPacketFragment outofdateRedPacketFragment);
    void inject(UseNewRedPacketActivity useNewRedPacketActivity);
}
