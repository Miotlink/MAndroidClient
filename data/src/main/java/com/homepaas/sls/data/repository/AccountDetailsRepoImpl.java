package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.AccountDetailsRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.AccountDetailItemEntry;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.domain.repository.AccountDetailsRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/6.
 */
@Singleton
public class AccountDetailsRepoImpl implements AccountDetailsRepo {

    @Inject
    public AccountDetailsRepoImpl() {
    }

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    RestApiHelper restApiHelper;

    @Override
    public Observable<AccountDetailItemEntry> getAccountDetail(int id) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            AccountDetailsRequest request = new AccountDetailsRequest(token, id);
            return restApiHelper.restApi()
                    .getAccountDetails(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<AccountDetailItemEntry>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
