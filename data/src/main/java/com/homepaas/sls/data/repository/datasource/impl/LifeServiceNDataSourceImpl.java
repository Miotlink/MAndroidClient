package com.homepaas.sls.data.repository.datasource.impl;

import android.util.Log;

import com.homepaas.sls.data.entity.LifeServiceEntity;
import com.homepaas.sls.data.entity.mapper.LifeServiceEntityMapper;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.LifeServiceDataSegment;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.repository.datasource.LifeServiceNDataSource;
import com.homepaas.sls.domain.entity.LifeService;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * on 2016/3/2 0002
 *
 * @author zhudongjie .
 */
@Singleton
public class LifeServiceNDataSourceImpl implements LifeServiceNDataSource {

    private static final String TAG = "LifeServiceNDataSource";

    private RestApiHelper apiHelper;

    private LifeServiceEntityMapper entityMapper;

    @Inject
    public LifeServiceNDataSourceImpl(RestApiHelper apiHelper, LifeServiceEntityMapper entityMapper) {
        this.apiHelper = apiHelper;
        this.entityMapper = entityMapper;
    }


    @Override
    public List<LifeService> getLifeServiceList() throws GetNetworkDataException, ResponseMetaDataException {
        try {
            Response<ResponseWrapper<LifeServiceDataSegment>> response = apiHelper.getLifeServiceList();
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    List<LifeServiceEntity> services = response.body().data.getServices();
                    return entityMapper.transform(services);
                }
                throw new ResponseMetaDataException(meta.getErrorMsg());
            }
            throw new GetNetworkDataException("错误响应码: " + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //impossible
            Log.e(TAG, "getLifeServiceList: ", e);
            return null;
        }

    }

    @Override
    public List<LifeService> getHotLifeServiceList() throws GetNetworkDataException, ResponseMetaDataException {
        try {
            Response<ResponseWrapper<LifeServiceDataSegment>> response = apiHelper.getHotServiceList();
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    List<LifeServiceEntity> services = response.body().data.getServices();
                    return entityMapper.transform(services);
                }
                throw new ResponseMetaDataException(meta.getErrorMsg());
            }
            throw new GetNetworkDataException("错误响应码: " + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }catch (ResponseMetaAuthException e) {
            //impossible
            Log.e(TAG, "getLifeServiceList: ", e);
            return null;
        }
    }
}
