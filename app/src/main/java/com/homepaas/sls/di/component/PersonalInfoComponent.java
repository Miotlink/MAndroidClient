package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.PersonalInfoModule;
import com.homepaas.sls.di.module.QrCodeModule;
import com.homepaas.sls.di.module.TokenModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.jsinterface.JavaScriptObject;
import com.homepaas.sls.ui.homepage_3_3_0.DetailWebActivity;
import com.homepaas.sls.ui.personalcenter.WriteBackInvitationActivity;
import com.homepaas.sls.ui.personalcenter.personalmessage.ModifyNicknameFragment;
import com.homepaas.sls.ui.personalcenter.personalmessage.PersonalInfoFragment;
import com.homepaas.sls.ui.personalcenter.personalmessage.PersonalMessageActivity;
import com.homepaas.sls.ui.qrcode.MyQrCodeActivity;

import dagger.Component;

/**
 * Created by Administrator on 2015/12/24.
 *
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PersonalInfoModule.class, TokenModule.class})
public interface PersonalInfoComponent extends ActivityComponent {

    void inject(PersonalInfoFragment fragment);

    void inject(PersonalMessageActivity personalMessageActivity);

    void inject(ModifyNicknameFragment fragment);

    void inject(JavaScriptObject javaScriptObject);

    void inject(WriteBackInvitationActivity activity);

    void inject(DetailWebActivity detailWebActivity);

}
