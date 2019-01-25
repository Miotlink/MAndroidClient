package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.NewFirstOpenAppInfo;
import com.homepaas.sls.domain.repository.FirstOpenAppInfoRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/8/14.
 */

public class FirstOpenAppInfoImpl implements FirstOpenAppInfoRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public FirstOpenAppInfoImpl() {
    }


    @Override
    public Observable<NewFirstOpenAppInfo> getFirstOpenAppInfo() {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            TokenRequest request = new TokenRequest(token);
            return apiHelper.restApi()
                    .firstOpenAppInfo(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<NewFirstOpenAppInfo>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
