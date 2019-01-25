package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.AddRecommendInfoRequest;
import com.homepaas.sls.data.repository.datasource.AddRecommendInfoDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.exception.AuthException;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Sherily on 2016/10/17.
 */
@Singleton
public class AddRecommendInfoDataSourceImpl implements AddRecommendInfoDataSource{

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public AddRecommendInfoDataSourceImpl(RestApiHelper apiHelper) {
    }

    @Override
    public String addRecommendInfo( String token, String code) throws GetNetworkDataException, AuthException, ResponseMetaAuthException, ResponseMetaDataException {
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.addRecommendInfo(token, code);
            if (response.code() == 200){
                Meta meta = response.body().meta;
                if (meta.isValid()){
                    return response.body().meta.getErrorMsg();
                }else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }
}
