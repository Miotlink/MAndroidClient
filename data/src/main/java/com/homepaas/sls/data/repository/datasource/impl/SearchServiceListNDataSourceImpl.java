package com.homepaas.sls.data.repository.datasource.impl;

import android.util.Log;

import com.homepaas.sls.data.entity.mapper.BusinessDataMapper;
import com.homepaas.sls.data.entity.mapper.WorkerDataMapper;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.NoSearchServiceException;
import com.homepaas.sls.data.exception.OutOfSearchServiceException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.SearchServiceBody;
import com.homepaas.sls.data.network.dataformat.request.ServiceRequest;
import com.homepaas.sls.data.repository.datasource.SearchServiceListNDataSource;
import com.homepaas.sls.domain.entity.ServiceInfo;
import com.homepaas.sls.domain.param.SearchParameter;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * on 2016/1/29 0029
 *
 * @author zhudongjie .
 */
@Singleton
public class SearchServiceListNDataSourceImpl implements SearchServiceListNDataSource {

    private static final String TAG = SearchServiceListNDataSourceImpl.class.getSimpleName();

    @Inject
    RestApiHelper apiHelper;

    @Inject
    BusinessDataMapper businessMapper;

    @Inject
    WorkerDataMapper workerMapper;

    @Inject
    public SearchServiceListNDataSourceImpl() {
    }

    @Override
    public ServiceInfo getService(SearchParameter param, String token) throws GetNetworkDataException, ResponseMetaDataException, NoSearchServiceException, OutOfSearchServiceException {

        ServiceRequest request = new ServiceRequest(param.latitude, param.longitude, param.serviceType, param.queryContent, param.queryType, param.serviceId);
        request.setToken(token);
        try {
            Response<ResponseWrapper<SearchServiceBody>> response = apiHelper.getSearchServiceList(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    SearchServiceBody data = response.body().data;
                    ServiceInfo serviceInfo = new ServiceInfo();

                    return serviceInfo;
                } else if (Meta.CODE_NO_SERVICE.equals(meta.getErrorCode())) {
                    throw new NoSearchServiceException(meta.getErrorMsg());
                } else if (Meta.CODE_OUT_OF_SERVICE.equals(meta.getErrorCode())) {
                    throw new OutOfSearchServiceException(meta.getErrorMsg());
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码:" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //impossible
            Log.e(TAG, "getService: ", e);
            return null;
        }
    }
}
