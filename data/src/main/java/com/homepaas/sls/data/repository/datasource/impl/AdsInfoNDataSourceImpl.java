package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.repository.datasource.AdsInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.AdsInfo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Administrator on 2016/12/27.
 */
@Singleton
public class AdsInfoNDataSourceImpl implements AdsInfoNDataSource {
    private RestApiHelper restApiHelper;


    @Inject
    public AdsInfoNDataSourceImpl(RestApiHelper restApiHelper) {
        this.restApiHelper = restApiHelper;
    }

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Override
    public AdsInfo getAdsInfo() throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<AdsInfo>> response = restApiHelper.getAdsInfo(token);
            if (response.code() == 200){
                Meta meta = response.body().meta;
                if (meta.isValid()){
                    return response.body().data;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new ResponseMetaDataException("错误响应码:" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
