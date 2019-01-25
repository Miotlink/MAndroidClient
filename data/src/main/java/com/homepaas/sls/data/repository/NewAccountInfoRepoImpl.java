package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.AccountInfo;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.domain.repository.NewAccountInfoRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/6.
 */

public class NewAccountInfoRepoImpl implements NewAccountInfoRepo {

    @Inject
    public NewAccountInfoRepoImpl() {

    }

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    RestApiHelper restApiHelper;

    @Override
    public Observable<NewAccountInfo> getAccountInfo() {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            TokenRequest request = new TokenRequest(token);
            return restApiHelper.restApi()
                    .getAccountInfo(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<NewAccountInfo>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
