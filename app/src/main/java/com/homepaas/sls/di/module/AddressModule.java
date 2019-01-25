package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.AddServiceAddressInteractor;
import com.homepaas.sls.domain.interactor.DeleteServiceAddressInteractor;
import com.homepaas.sls.domain.interactor.GetServiceAddressInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.interactor.UpdateServiceAddressInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CJJ on 2016/9/13.
 *
 */
@Module
public class AddressModule {

    @ActivityScope
    @Provides
    Interactor provideServiceAddressInteractor(GetServiceAddressInteractor interactor){
        return interactor;
    }

    @ActivityScope
    @Provides
    Interactor providerAddServiceAddressInteractor(AddServiceAddressInteractor intractor)
    {
        return intractor;
    }

    @ActivityScope
    @Provides
    Interactor provideDeleteServiceAddressInteractor(DeleteServiceAddressInteractor intractor)
    {
        return intractor;
    }

    @ActivityScope
    @Provides
    Interactor provideUpdateServiceAddressInteractor(UpdateServiceAddressInteractor interactor)
    {
        return interactor;
    }
}
