package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.CreateDirectPayExRequest;
import com.homepaas.sls.data.network.dataformat.request.WorkerServicePriceListRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.CreateDirectPayExEntity;
import com.homepaas.sls.domain.entity.WorkerServicePriceListEntity;
import com.homepaas.sls.domain.repository.CreateDirectPayExRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/2/17.
 */

public class CreateDirectPayExImpl implements CreateDirectPayExRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public CreateDirectPayExImpl() {
    }

    @Override
    public Observable<CreateDirectPayExEntity> getCreateDirectPayEx(String receiverID, String receiverType, String paySource, String totalMoney, String activityNgId) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            CreateDirectPayExRequest createDirectPayExRequest = new CreateDirectPayExRequest(token, receiverID, receiverType, paySource, totalMoney, activityNgId);
            return apiHelper.restApi()
                    .getCreateDirectPayEx(createDirectPayExRequest)
                    .flatMap(new RestApiHelper.ResponseParseFunc<CreateDirectPayExEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }

        return null;
    }
}

