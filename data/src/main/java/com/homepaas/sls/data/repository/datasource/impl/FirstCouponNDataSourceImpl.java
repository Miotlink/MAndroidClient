package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.FirstCouponNDataSource;
import com.homepaas.sls.domain.entity.FirstOpenAppInfo;
import com.homepaas.sls.domain.entity.ShareInfo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Administrator on 2016/7/12.
 */
@Singleton
public class FirstCouponNDataSourceImpl implements FirstCouponNDataSource {

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public FirstCouponNDataSourceImpl(){}

    @Override
    public FirstOpenAppInfo getFirstCoupon(String token) throws ResponseMetaDataException, ResponseMetaAuthException, GetNetworkDataException {
        try {
            TokenRequest request = new TokenRequest(token);
            Response<ResponseWrapper<FirstOpenAppInfo>> response = apiHelper.getFirstCoupon(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data;

                }else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
                throw new ResponseMetaDataException("错误响应码:" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }
}
