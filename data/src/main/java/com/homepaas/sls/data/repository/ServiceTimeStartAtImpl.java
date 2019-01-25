package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.QueryServicePriceRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.QueryServicePriceEntry;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.ServiceTimeStartAtEntry;
import com.homepaas.sls.domain.repository.ServiceTimeStartAtRepo;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/3/28.
 */

public class ServiceTimeStartAtImpl implements ServiceTimeStartAtRepo {
    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public ServiceTimeStartAtImpl() {
    }


    @Override
    public Observable<ServiceTimeStartAtEntry> getServiceTimeStartAt(String serviceId, String longitude, String latitude) {
        String token;
        try {
            token=personalInfoPDataSource.getToken();
            QueryServicePriceRequest request=new QueryServicePriceRequest(token,serviceId,longitude,latitude);
            return apiHelper.restApi()
                    .getServiceTimeStartAt(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<ServiceTimeStartAtEntry>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
