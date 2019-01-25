package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.entity.mapper.WorkerTagsInfoMapper;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.WorkerTagsBody;
import com.homepaas.sls.data.network.dataformat.request.GetWorkerTagsRequest;
import com.homepaas.sls.data.repository.datasource.WorkerTagsNDataSource;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.WorkerTagsInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Sheirly on 2016/9/8.
 */
@Singleton
public class WorkerTagsNDataSourceImpl implements WorkerTagsNDataSource {

    private RestApiHelper restApiHelper;
    private WorkerTagsInfoMapper workerTagsInfoMapper;

    @Inject
    public WorkerTagsNDataSourceImpl(RestApiHelper restApiHelper,WorkerTagsInfoMapper workerTagsInfoMapper) {
        this.restApiHelper = restApiHelper;
        this.workerTagsInfoMapper = workerTagsInfoMapper;
    }

    @Override
    public GetWorkerTagsInfo getWorkerTagsInfo(String workerId) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        GetWorkerTagsRequest request = new GetWorkerTagsRequest(workerId);
        Response<ResponseWrapper<WorkerTagsBody>> response = null;
        GetWorkerTagsInfo getWorkerTagsInfo = null;
        try {
            response = restApiHelper.getWorkerTags(request);
            if (response.code() == 200){
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    WorkerTagsBody data = response.body().data;
                    if (data != null){
                        getWorkerTagsInfo = workerTagsInfoMapper.transform(data);
                    }/*else {
                        getWorkerTagsInfo = new GetWorkerTagsInfo();
                        getWorkerTagsInfo.setCount(0);
                        getWorkerTagsInfo.setWorkerTagsInfos(new ArrayList<WorkerTagsInfo>());
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

        return getWorkerTagsInfo;
    }
}
