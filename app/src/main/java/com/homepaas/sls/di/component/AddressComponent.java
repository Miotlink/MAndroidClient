package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.AddressModule;
import com.homepaas.sls.di.module.ServiceModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.order.directOrder.AddressManageActivity;
import com.homepaas.sls.ui.order.directOrder.EditAddressActivity;

import dagger.Component;

/**
 * Created by CJJ on 2016/9/13.
 *
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {AddressModule.class, ServiceModule.class})
public interface AddressComponent {
    void inject(AddressManageActivity activity);

    void inject(EditAddressActivity activity);
}
