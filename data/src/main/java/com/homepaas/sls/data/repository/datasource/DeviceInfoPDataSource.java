package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;

/**
 * on 2016/2/2 0002
 *
 * @author zhudongjie .
 */
public interface DeviceInfoPDataSource {
    String getDeviceId() throws GetPersistenceDataException;

    String getPushDeviceId()throws GetPersistenceDataException;

    boolean savePushDeviceId(String clientId)throws PersistDataException;
}
