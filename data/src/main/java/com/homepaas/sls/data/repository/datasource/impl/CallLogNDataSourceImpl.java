package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.CallLogRequest;
import com.homepaas.sls.data.repository.datasource.CallLogNDataSource;
import com.homepaas.sls.domain.entity.CallLog;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * on 2016/2/26 0026
 *
 * @author zhudongjie .
 */
@Singleton
public class CallLogNDataSourceImpl implements CallLogNDataSource {

    private RestApiHelper apiHelper;

    @Inject
    public CallLogNDataSourceImpl(RestApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Override
    public void uploadCallLog(CallLog callLog, String token, String deviceId) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        CallLogRequest request = new CallLogRequest();
        request.setId(callLog.getId());
        request.setPhone(callLog.getPhoneNumber());
        request.setCallTime(String.valueOf(callLog.getDuration()));
        request.setCallType(callLog.getType());
        request.setIsDial(callLog.isDialled());
        request.setDeviceId(deviceId);
        request.setToken(token);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.sendCallLog(request);
            if (response.code() == 200) {
                if (!response.body().meta.isValid()) {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码：" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }
}
