package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.Account;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.LoginInfoRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/6/7 0007
 *
 * @author zhudongjie
 */
@Singleton
public class LoginInfoRepoImpl implements LoginInfoRepo {


    PersonalInfoPDataSource pDataSource;

    @Inject
    public LoginInfoRepoImpl(PersonalInfoPDataSource pDataSource) {
        this.pDataSource = pDataSource;
    }

    @Override
    public List<Account> getAccountList() throws GetDataException {
        try {
            return pDataSource.getAccounts();
        } catch (GetPersistenceDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }
    }
}
