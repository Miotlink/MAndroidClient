package com.homepaas.sls.data.repository.datasource.impl;

import android.accounts.NetworkErrorException;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.DataSegmentName;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.repository.datasource.FirstOrderInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.exception.AuthException;

import java.io.IOException;

import javax.inject.Inject;

import retrofit.Response;

/**
 * Created by Sherily on 2017/2/15.
 */

public class FirstOrderInfoNDataSourceImpl implements FirstOrderInfoNDataSource {


    @Inject
    public FirstOrderInfoNDataSourceImpl(RestApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Override
    public IsFirstOrderInfo getIsFirstOrderInfo() throws AuthException, GetNetworkDataException, ResponseMetaDataException {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<IsFirstOrderInfo>> response = apiHelper.getIsFirstOrderInfo(token);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    IsFirstOrderInfo isFirstOrderInfo = response.body().data;
                    return isFirstOrderInfo;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new ResponseMetaDataException("错误响应码:" + response.code());
        } catch (GetPersistenceDataException |ResponseMetaAuthException e ) {
            throw new AuthException(e);
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }
}
