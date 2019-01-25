package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.NoSearchServiceException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.AvatarRequest;
import com.homepaas.sls.data.repository.datasource.AvatarsNDataSource;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.exception.AuthException;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Administrator on 2016/9/18.
 */
@Singleton
public class AvatarsNDataSourceImpl implements AvatarsNDataSource {
    private RestApiHelper restApiHelper;

    @Inject
    public AvatarsNDataSourceImpl(RestApiHelper restApiHelper) {
        this.restApiHelper = restApiHelper;
    }

    @Override
    public AvatarsEntity getAvatars(String type, String id) throws GetNetworkDataException, AuthException, ResponseMetaAuthException, ResponseMetaDataException {
        AvatarRequest request = new AvatarRequest(type, id);
        try {
           Response<ResponseWrapper<AvatarsEntity>> response = restApiHelper.getAvatar(request);
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
        }
    }
}
