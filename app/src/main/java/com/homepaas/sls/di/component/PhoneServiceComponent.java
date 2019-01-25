package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.CallLogModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.phone.PhoneStateService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * on 2016/3/2 0002
 *
 * @author zhudongjie .
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class ,modules = {CallLogModule.class})
public interface PhoneServiceComponent {

    void inject(PhoneStateService service);
}
