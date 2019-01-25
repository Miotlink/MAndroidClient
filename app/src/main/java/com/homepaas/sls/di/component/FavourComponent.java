package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.FavourModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.redpacket.PacketActivityActivity;
import com.homepaas.sls.ui.redpacket.RedPacketActivity;

import dagger.Component;

/**
 * Created by Administrator on 2016/4/28.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {FavourModule.class})
public interface FavourComponent {

    void inject(PacketActivityActivity activity);
    void inject(RedPacketActivity activity);
}
