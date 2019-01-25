package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.CallLog;

/**
 * on 2016/2/26 0026
 *
 * @author zhudongjie .
 */
public interface CallLogNDataSource {

    void uploadCallLog(CallLog callLog, String token, String deviceId) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;
}
