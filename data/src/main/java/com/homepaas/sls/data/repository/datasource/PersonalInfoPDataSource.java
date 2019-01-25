package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.DelPersistenceDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.InvalidPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.domain.entity.Account;
import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.param.LoginParam;

import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 */

public interface PersonalInfoPDataSource {

    public PersonalInfo get()
            throws GetPersistenceDataException, InvalidPersistenceDataException;

    public int save(PersonalInfo personalInfo)
            throws PersistDataException;

    public int delete(PersonalInfo personalInfo)
            throws DelPersistenceDataException;

    int deleteAll() throws DelPersistenceDataException;

    void saveToken(String token) throws PersistDataException;

    void clearToken() throws PersistDataException;

    String getToken() throws GetPersistenceDataException;
    String getUserToken();

    void saveTelphone(String telphone) throws PersistDataException;
    void clearTelPhone() throws PersistDataException;
    String getTelphone() throws GetPersistenceDataException;

    @Deprecated
    boolean saveAccount(LoginParam account) throws PersistDataException;

    @Deprecated
    LoginParam getAccount() throws GetPersistenceDataException, InvalidPersistenceDataException;

    boolean saveAccount(Account account)throws PersistDataException;

    List<Account> getAccounts()throws GetPersistenceDataException;
}
