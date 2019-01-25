package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.entity.mapper.BusinessTagsInfoMapper;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.BusinessTagsBody;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.WorkerTagsBody;
import com.homepaas.sls.data.network.dataformat.request.GetBusinessTagsRequest;
import com.homepaas.sls.data.network.dataformat.request.GetWorkerTagsRequest;
import com.homepaas.sls.data.repository.datasource.BusinessTagsNDataSource;
import com.homepaas.sls.domain.entity.BusinessTagsInfo;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.WorkerTagsInfo;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Sheirly on 2016/9/9.
 */
@Singleton
public class BusinessTagsNDataSourceImpl implements BusinessTagsNDataSource {
    private RestApiHelper restApiHelper;
    private BusinessTagsInfoMapper businessTagsInfoMapper;

    @Inject
    public BusinessTagsNDataSourceImpl(RestApiHelper restApiHelper, BusinessTagsInfoMapper businessTagsInfoMapper) {
        this.restApiHelper = restApiHelper;
        this.businessTagsInfoMapper = businessTagsInfoMapper;
    }

    @Override
    public GetBusinessTagsInfo getBusinessTagsInfo(String workerId) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        GetBusinessTagsRequest request = new GetBusinessTagsRequest(workerId);
        Response<ResponseWrapper<BusinessTagsBody>> response = null;
        GetBusinessTagsInfo geBusinessTagsInfo = null;
        try {
            response = restApiHelper.getBusinessTags(request);
            if (response.code() == 200){
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    BusinessTagsBody data = response.body().data;
                    if (data != null){
                        geBusinessTagsInfo = businessTagsInfoMapper.transform(data);
                    }/*else {
                        geBusinessTagsInfo = new GetBusinessTagsInfo();
                        geBusinessTagsInfo.setCount(0);
                        geBusinessTagsInfo.setBusinessTagsInfos(new ArrayList<BusinessTagsInfo>());
                    }*/
//                    return (GetWorkerTagsInfo) response.body().data.getWorkerTagsInfos();
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("Wrong response code:" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }

        return geBusinessTagsInfo;
    }
}
