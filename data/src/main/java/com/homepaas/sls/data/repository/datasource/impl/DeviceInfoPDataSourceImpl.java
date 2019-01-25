package com.homepaas.sls.data.repository.datasource.impl;

import android.content.SharedPreferences;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.GetSystemInfoException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.repository.datasource.DeviceInfoPDataSource;
import com.homepaas.sls.data.system.Device;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;

/**
 * on 2016/2/2
 * 0002
 *
 * @author zhudongjie .
 */
@Singleton
public class DeviceInfoPDataSourceImpl implements DeviceInfoPDataSource {

    @Inject
    Lazy<Device> device;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    public DeviceInfoPDataSourceImpl() {
    }

    @Override
    public String getDeviceId() throws GetPersistenceDataException {
        String id = sharedPreferences.getString("device_id", null);
        if (id == null) {
            try {
                id = device.get().getDeviceId();
                sharedPreferences.edit().putString("device_id", id).apply();
            } catch (GetSystemInfoException e) {
                throw new GetPersistenceDataException(e);
            }
        }
        return id;
    }

    @Override
    public String getPushDeviceId() throws GetPersistenceDataException {
        return sharedPreferences.getString("clientId", null);
    }

    @Override
    public boolean savePushDeviceId(String clientId) throws PersistDataException {
        return sharedPreferences.edit().putString("clientId", clientId).commit();
    }
}
