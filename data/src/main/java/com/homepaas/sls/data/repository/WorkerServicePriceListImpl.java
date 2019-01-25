package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApi;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.WorkerServicePriceListRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.MerchantServicePriceListEntity;
import com.homepaas.sls.domain.entity.WorkerServicePriceListEntity;
import com.homepaas.sls.domain.repository.WorkerServicePriceListRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/2/13.
 */

public class WorkerServicePriceListImpl implements WorkerServicePriceListRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public WorkerServicePriceListImpl() {
    }

    @Override
    public Observable<WorkerServicePriceListEntity> getWorkerServicePriceList(String id) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            WorkerServicePriceListRequest workerServicePriceListRequest = new WorkerServicePriceListRequest(token, id);
            return apiHelper.restApi()
                    .getWorkerServicePriceList(workerServicePriceListRequest)
                    .flatMap(new RestApiHelper.ResponseParseFunc<WorkerServicePriceListEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }

        return null;
    }
}
