package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.repository.datasource.PopuVerNDataSource;
import com.homepaas.sls.domain.entity.PopupVer;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Administrator on 2016/12/27.
 */
@Singleton
public class PopuverNDataSourceImpl implements PopuVerNDataSource {
    private RestApiHelper restApiHelper;
    @Inject
    public PopuverNDataSourceImpl(RestApiHelper restApiHelper) {
        this.restApiHelper = restApiHelper;
    }

    @Override
    public PopupVer getPopuVer() throws ResponseMetaAuthException, ResponseMetaDataException, GetNetworkDataException {
        try {
            Response<ResponseWrapper<PopupVer>> response = restApiHelper.getPopuVer();
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
