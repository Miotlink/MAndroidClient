package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.BannerModule;
import com.homepaas.sls.di.module.BusinessDetailModule;
import com.homepaas.sls.di.module.DescriptionModule;
import com.homepaas.sls.di.module.FirstCouponModule;
import com.homepaas.sls.di.module.FirstOrderInfoModule;
import com.homepaas.sls.di.module.LifeServiceModule;
import com.homepaas.sls.di.module.LoginModule;
import com.homepaas.sls.di.module.MessageModule;
import com.homepaas.sls.di.module.OrderInfoModule;
import com.homepaas.sls.di.module.PersonalInfoModule;
import com.homepaas.sls.di.module.SearchServiceModule;
import com.homepaas.sls.di.module.ServiceModule;
import com.homepaas.sls.di.module.ShareModule;
import com.homepaas.sls.di.module.TokenModule;
import com.homepaas.sls.di.module.WorkerDetailModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.MainActivity;
import com.homepaas.sls.ui.footcard.FootCardFragment;
import com.homepaas.sls.ui.fragment.NewPersonalCenterFragment;
import com.homepaas.sls.ui.order.manage.OrderAllFragment;
import com.homepaas.sls.ui.order.manage.OrderCurrentFragment;
import com.homepaas.sls.ui.order.manage.OrderHistoryFragment;
import com.homepaas.sls.ui.order.manage.OrderManageFragment;
import com.homepaas.sls.ui.order.manage.OrderToConfirmFragment;
import com.homepaas.sls.ui.order.manage.OrderToEvaluateFragment;
import com.homepaas.sls.ui.order.manage.OrderToPayFragment;
import com.homepaas.sls.ui.order.manage.OrderToTakeFragment;
import com.homepaas.sls.ui.search.MainContentFragment;

import dagger.Component;

/**
 * on 2016/1/19 0019
 *
 * @author zhudongjie .
 */
// XXX: 2016/7/8 0008 重新整理Module
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LoginModule.class,
        SearchServiceModule.class, PersonalInfoModule.class, MessageModule.class,LifeServiceModule.class,
        BusinessDetailModule.class, WorkerDetailModule.class, FirstCouponModule.class, BannerModule.class,
        TokenModule.class,OrderInfoModule.class, DescriptionModule.class, ServiceModule.class,
        ShareModule.class, FirstOrderInfoModule.class})
public interface MainComponent extends ActivityComponent {
    void inject(MainActivity mainActivity);

    void inject(MainContentFragment fragment);
    void inject(FootCardFragment fragment);
    void inject(OrderManageFragment fragment);
    void inject(OrderAllFragment fragment);
    void inject(OrderToTakeFragment fragment);
    void inject(OrderToPayFragment fragment);
    void inject(OrderToConfirmFragment fragment);
    void inject(OrderToEvaluateFragment fragment);
    void inject(NewPersonalCenterFragment fragment);
    void inject(OrderCurrentFragment fragment);
    void inject(OrderHistoryFragment fragment);




//    void inject(ServiceListFragment fragment);

}
