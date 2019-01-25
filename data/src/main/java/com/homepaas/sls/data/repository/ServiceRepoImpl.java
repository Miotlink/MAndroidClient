package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.ServiceIdRequest;
import com.homepaas.sls.data.network.dataformat.request.ServiceTypeIdRequest;
import com.homepaas.sls.data.repository.datasource.ServiceNDataSource;
import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.SystemConfigEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.param.PriceParam;
import com.homepaas.sls.domain.repository.ServiceRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by CJJ on 2016/9/14.
 *
 */
@Singleton
public class ServiceRepoImpl implements ServiceRepo{

    @Inject
    ServiceNDataSource serviceNDataSource;

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public ServiceRepoImpl() {
    }

    @Override
    public List<ServiceScheduleEntity> getServiceDSchedule(String serviceTypId) throws AuthException {
        return serviceNDataSource.getServiceDSchedule(serviceTypId);
    }

    @Override
    public PriceEntity getServicePrice(PriceParam param) throws AuthException {
        return serviceNDataSource.getServicePrice(param);
    }

    @Override
    public ActivityNgInfoNew getAvailableActivity(String serviceTypeId) throws AuthException {
        return serviceNDataSource.getAvailableActivity(serviceTypeId);
    }

    @Override
    public SystemConfigEntity getConfig() throws AuthException {
        return serviceNDataSource.getConfig();
    }

    @Override
    public Observable<List<String>> getQty(String serviceTypeId) {
        ServiceIdRequest serviceIdRequest = new ServiceIdRequest(serviceTypeId);
        return apiHelper.restApi()
                .getQty(serviceIdRequest)
                .flatMap(new RestApiHelper.ResponseParseFunc<List<String>>());
    }
}
