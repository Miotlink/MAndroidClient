package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.SubmitApplyClaimsRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.repository.SubmitApplyClaimsRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/7/6.
 */

public class SubmitApplyClaimsImpl implements SubmitApplyClaimsRepo {
    @Inject
    public SubmitApplyClaimsImpl() {
    }
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Override
    public Observable<String> submitApplyClaims(String orderId, String reasonType, String workerLaterTime, String reason) {
        String token;
        try {
            token=personalInfoPDataSource.getToken();
            SubmitApplyClaimsRequest request=new SubmitApplyClaimsRequest(token,orderId,reasonType,workerLaterTime,reason);
            return apiHelper.restApi()
                    .submitApplyClaims(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<String>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
