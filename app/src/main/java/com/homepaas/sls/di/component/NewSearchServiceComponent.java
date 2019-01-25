package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.search.CommonSearchServiceActivity;
import com.homepaas.sls.ui.search.NewSearchActivity;

import dagger.Component;

/**
 * Created by JWC on 2016/12/20.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules ={CameraModule.class} )
public interface NewSearchServiceComponent {
    void inject(NewSearchActivity activity);
    void inject(CommonSearchServiceActivity commonSearchActivity);

}
