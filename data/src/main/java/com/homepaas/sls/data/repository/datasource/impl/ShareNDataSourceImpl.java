package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.ShareInfoBody;
import com.homepaas.sls.data.network.dataformat.request.AddShareRecord;
import com.homepaas.sls.data.network.dataformat.request.GetShareInfoRequest;
import com.homepaas.sls.data.repository.datasource.ShareNDataSource;
import com.homepaas.sls.domain.entity.ShareInfo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * on 2016/7/1 0001
 *
 * @author zhudongjie
 */
@Singleton
public class ShareNDataSourceImpl implements ShareNDataSource {

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public ShareNDataSourceImpl() {
    }

    @Override
    public ShareInfo get(String token, int id) throws GetNetworkDataException, ResponseMetaDataException, ResponseMetaAuthException {
        GetShareInfoRequest request = new GetShareInfoRequest();
        request.setToken(token);
        request.setId(String.valueOf(id));
        try {
            Response<ResponseWrapper<ShareInfoBody>> response = apiHelper.getShareInfo(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                /**
                 * 返回结果编号, 0 表示无错误, 2004 表示用户权限认证失败, 返回负数表示各种错误
                 */
                if (meta.isValid()) { //
                    return response.body().data.shareInfo;
                } else {
                    //
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }


    @Override
    public String addShareRecord(String token, int id) throws GetNetworkDataException, ResponseMetaDataException, ResponseMetaAuthException {
        AddShareRecord request = new AddShareRecord();
        request.setId(String.valueOf(id));
        request.setToken(token);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.addShareRecord(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().meta.getErrorMsg();
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }
}
