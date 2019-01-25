package com.homepaas.sls.di.module;

import android.content.Context;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.navigation.CameraTools;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/4/15.
 */
@Module
public class CameraModule {

    private Context context;

    public CameraModule(Context context)
    {
        this.context = context;
    }

    @Provides
    @ActivityScope
    Object provideCameraTools(CameraTools cameraTools){
        return cameraTools;
    }

    @ActivityScope
    @Provides
    Context provideContext(){return context;}
}
