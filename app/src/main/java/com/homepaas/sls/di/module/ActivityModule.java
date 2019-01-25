package com.homepaas.sls.di.module;

import android.app.Activity;

import com.homepaas.sls.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2015/12/22.
 *
 */

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @ActivityScope
    Activity activity() {
        return activity;
    }

}
