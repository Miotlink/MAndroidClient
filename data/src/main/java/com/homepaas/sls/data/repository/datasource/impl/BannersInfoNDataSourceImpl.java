package com.homepaas.sls.data.repository.datasource.impl;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.repository.datasource.BannersInfoNDataSource;
import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.BannersInfo;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Sherily on 2016/9/10.
 */
@Singleton
public class BannersInfoNDataSourceImpl implements BannersInfoNDataSource {
    private RestApiHelper restApiHelper;

    @Inject
    public BannersInfoNDataSourceImpl(RestApiHelper restApiHelper) {
        this.restApiHelper = restApiHelper;
    }

    @Override
    public List<BannerInfo> getBannersInfo() throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {

        try {
            Response<ResponseWrapper<List<BannerInfo>>> response = restApiHelper.getBannersInfo();
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
