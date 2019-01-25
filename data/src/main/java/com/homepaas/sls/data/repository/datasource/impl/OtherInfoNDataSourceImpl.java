package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.UploadQrCodeRequest;
import com.homepaas.sls.data.repository.datasource.OtherInfoNDataSource;
import com.homepaas.sls.domain.entity.PushInfo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * on 2016/7/11 0011
 *
 * @author zhudongjie
 */
@Singleton
public class OtherInfoNDataSourceImpl implements OtherInfoNDataSource {

    private RestApiHelper apiHelper;

    @Inject
    public OtherInfoNDataSourceImpl(RestApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Override
    public PushInfo loadUrl(String token, String url) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        UploadQrCodeRequest request = new UploadQrCodeRequest(token,url);
        try {
            Response<ResponseWrapper<PushInfo>> response = apiHelper.uploadQrCodeUrl(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码:" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }
}
