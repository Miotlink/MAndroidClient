package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.ServiceNDataSource;
import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.SystemConfigEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.param.PriceParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by CJJ on 2016/9/14.
 *
 */
@Singleton
public class ServiceNDataSourceImpl implements ServiceNDataSource{

    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public ServiceNDataSourceImpl() {
    }

    @Override
    public List<ServiceScheduleEntity> getServiceDSchedule(String serviceTypId) throws AuthException {
        try {
            Response<ResponseWrapper<List<ServiceScheduleEntity>>> response = apiHelper.getServiceDSchedule(serviceTypId);
            Meta meta = response.body().meta;
            if (meta.isValid())
            {
                List<ServiceScheduleEntity> data = response.body().data;
                return data;
            }
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PriceEntity getServicePrice(PriceParam param) throws AuthException {
        try {
            param.setToken(personalInfoPDataSource.getToken());
            Response<ResponseWrapper<PriceEntity>> response = apiHelper.getServicePrice(param);
            Meta meta = response.body().meta;
            if (meta.isValid())
            {
//                PriceEntity data = response.body().data;
                return response.body().data;
            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ResponseMetaAuthException e) {
            throw  new AuthException(e);
        } catch (GetPersistenceDataException e) {
            throw new AuthException(e);
        }
        return null;
    }

    @Override
    public ActivityNgInfoNew getAvailableActivity(String serviceTypeId) throws AuthException {
        try {
            String token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<ActivityNgInfoNew>> response = apiHelper.getAvailableActivity(token,serviceTypeId);
            if (response.body().meta.isValid())
            {
                ActivityNgInfoNew data = response.body().data;
                return data;
            }
        } catch (GetPersistenceDataException |ResponseMetaAuthException e) {
                throw new AuthException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SystemConfigEntity getConfig() throws AuthException {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<SystemConfigEntity>> response = apiHelper.getSystemConfig(token);
            if (response.body().meta.isValid())
            {

            }

        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
           throw  new AuthException(e);
        }
        return null;
    }

    @Override
    @Deprecated
    public List<String> getQty(String serviceTypeId) {
        return new ArrayList<>();
    }

}
