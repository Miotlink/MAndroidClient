package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.CreateDirectPayExRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.CheckIsReceivedRedCoupsEntry;
import com.homepaas.sls.domain.entity.CreateDirectPayExEntity;
import com.homepaas.sls.domain.repository.CheckIsReceivedRedCoupsRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/3/8.
 */

public class CheckIsReceivedRedCoupsImpl implements CheckIsReceivedRedCoupsRepo {

    @Inject
    public CheckIsReceivedRedCoupsImpl() {
    }

    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;


    @Override
    public Observable<CheckIsReceivedRedCoupsEntry> getCheckIsReceivedRedCoups() {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            TokenRequest tokenRequest = new TokenRequest(token);
            return apiHelper.restApi()
                    .getCheckIsReceivedRedCoups(tokenRequest)
                    .flatMap(new RestApiHelper.ResponseParseFunc<CheckIsReceivedRedCoupsEntry>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
