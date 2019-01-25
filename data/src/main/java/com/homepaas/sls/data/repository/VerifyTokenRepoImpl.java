package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.VerifyTokenBody;
import com.homepaas.sls.domain.repository.VerifyTokenRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Sherily on 2017/3/13.
 */

public class VerifyTokenRepoImpl implements VerifyTokenRepo {
    @Inject
    public VerifyTokenRepoImpl() {
    }

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    RestApiHelper restApiHelper;
    @Override
    public Observable<VerifyTokenBody> verifyToken() {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            TokenRequest request = new TokenRequest(token);
            return restApiHelper.restApi()
                    .verifyToken(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<VerifyTokenBody>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
