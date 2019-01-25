package com.homepaas.sls.data.repository.datasource.impl;


import android.util.Log;

import com.homepaas.sls.data.entity.mapper.WorkerServiceTypeMapper;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.ServiceContentBody;
import com.homepaas.sls.data.network.dataformat.WorkerServiceTypeBody;
import com.homepaas.sls.data.network.dataformat.request.BusinessServiceRequest;
import com.homepaas.sls.data.repository.datasource.ServiceTypeListNDataSource;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 *
 *
 * @author CJJ
 * @time 2016/4/27    9:17
 */
@Singleton
public class ServiceTypeListNDataSourceImpl implements ServiceTypeListNDataSource{

    private static final String TAG = "ServiceTypeListNDataSourceImpl";

    @Inject
    WorkerServiceTypeMapper mapper;

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public ServiceTypeListNDataSourceImpl() {
    }

    @Override
    public WorkerServiceTypeInfo getWorkerServiceTypeInfo(String id) throws IOException {


        Response<ResponseWrapper<WorkerServiceTypeInfo>> response = apiHelper.getWorkerServiceTypeList(id);
        if (response.code() == 200)
            {

                try {
                    if (response.body().meta.isValid())
                    {
                        return response.body().data;
                    }
                } catch (ResponseMetaAuthException e) {
                    e.printStackTrace();
                }

            }
        return null;
    }

    @Override
    public BusinessServiceTypeInfo getBusinessServiceTypeInfo(String id) throws IOException {

        Response<ResponseWrapper<ServiceContentBody>> response = apiHelper.getBusinessServiceContents(new BusinessServiceRequest(id));
        if (response.code() == 200)
        {
            try {
                if (response.body().meta.isValid())
                {
                    return mapper.transform(response.body().data.serviceContents);
                }
            } catch (ResponseMetaAuthException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
