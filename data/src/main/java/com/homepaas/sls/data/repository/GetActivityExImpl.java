package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.GetActivityExRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.repository.GetActivityExRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/4/1.
 */

public class GetActivityExImpl implements GetActivityExRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public GetActivityExImpl() {
    }

    @Override
    public Observable<ActivityNgInfoNew> getGetActivityEx(String serviceId) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            GetActivityExRequest request = new GetActivityExRequest(token, serviceId);
            return apiHelper.restApi()
                    .getActivityEx(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<ActivityNgInfoNew>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
