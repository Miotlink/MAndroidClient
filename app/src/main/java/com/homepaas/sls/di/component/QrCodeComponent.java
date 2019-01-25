package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.LoginModule;
import com.homepaas.sls.di.module.PersonalInfoModule;
import com.homepaas.sls.di.module.QrCodeModule;
import com.homepaas.sls.di.module.TokenModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.qrcode.MyQrCodeActivity;

import dagger.Component;

/**
 * on 2016/7/13 0013
 *
 * @author zhudongjie
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {QrCodeModule.class, PersonalInfoModule.class, TokenModule.class, LoginModule.class})
public interface QrCodeComponent {

    void inject(MyQrCodeActivity activity);
}
