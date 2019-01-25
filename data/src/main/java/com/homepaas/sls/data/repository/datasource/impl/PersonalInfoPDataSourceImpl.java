package com.homepaas.sls.data.repository.datasource.impl;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.homepaas.sls.data.entity.AccountEntity;
import com.homepaas.sls.data.entity.PersonalInfoEntity;
import com.homepaas.sls.data.entity.mapper.PersonalInfoEntityMapper;
import com.homepaas.sls.data.exception.DelPersistenceDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.InvalidPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.data.validator.Validator;
import com.homepaas.sls.domain.entity.Account;
import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.param.LoginParam;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2015/12/23.
 */

@Singleton
public class PersonalInfoPDataSourceImpl implements PersonalInfoPDataSource {

    private static final String TOKEN_KEY = "tokenKey";
    private static final String TELPHONE_KEY = "telphoneKey";

    private Dao<PersonalInfoEntity, String> dao;

    private Validator<PersonalInfoEntity> validator;

    private Validator<AccountEntity> accountValidator;

    private Dao<AccountEntity, String> accountDao;

    private PersonalInfoEntityMapper mapper;

    private SharedPreferences sharedPreferences;

    @Inject
    public PersonalInfoPDataSourceImpl(Dao<PersonalInfoEntity, String> dao,
                                       Validator<PersonalInfoEntity> validator,
                                       Validator<AccountEntity> accountValidator, Dao<AccountEntity, String> accountDao, PersonalInfoEntityMapper mapper,
                                       SharedPreferences sharedPreferences) {
        this.dao = dao;
        this.validator = validator;
        this.accountValidator = accountValidator;
        this.accountDao = accountDao;
        this.mapper = mapper;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public PersonalInfo get() throws GetPersistenceDataException, InvalidPersistenceDataException {
        try {
            List<PersonalInfoEntity> entities = dao.queryForAll();
            if (entities == null || entities.isEmpty()) {
                throw new InvalidPersistenceDataException();
            }
            PersonalInfoEntity entity = entities.get(0);
            if (!validator.isValid(entity)) {
                deleteEntity(entity);
                throw new InvalidPersistenceDataException();
            }
            return mapper.transform(entity);
        } catch (SQLException e) {
            throw new GetPersistenceDataException();
        }
    }

    @Override
    public int save(PersonalInfo personalInfo) throws PersistDataException {
        if (personalInfo != null) {
            PersonalInfoEntity entity = mapper.transformToEntity(personalInfo);
            //设置持久化时间
            entity.setPersistedTime(System.currentTimeMillis());
            try {
                if (dao.idExists(entity.getAccount())) {
                    return dao.update(entity);
                } else {
                    return dao.create(entity);
                }
            } catch (SQLException e) {
                throw new PersistDataException(e);
            }
        }
        return 0;
    }

    @Override
    public int delete(PersonalInfo personalInfo) throws DelPersistenceDataException {
        if (personalInfo != null) {
            try {
                return internalDeleteEntity(personalInfo.getAccount());
            } catch (SQLException e) {
                throw new DelPersistenceDataException();
            }
        }
        return 0;
    }

    @Override
    public int deleteAll() throws DelPersistenceDataException {
        try {
            return dao.delete(dao.queryForAll());
        } catch (SQLException e) {
            throw new DelPersistenceDataException(e);
        }
    }


    @Override
    public void saveToken(String token) throws PersistDataException {
        if (!sharedPreferences.edit().putString(TOKEN_KEY, token).commit()) {
            throw new PersistDataException("Token 保存失败");
        }
    }

    @Override
    public void clearToken() throws PersistDataException {
        saveToken("");
    }

    @Override
    public String getToken() throws GetPersistenceDataException {
        try {
            String string = sharedPreferences.getString(TOKEN_KEY, "");
            return string;
        } catch (ClassCastException e) {
            throw new GetPersistenceDataException(e);
        }

    }

    @Override
    public String getUserToken() {
        String string = sharedPreferences.getString(TOKEN_KEY, "");
        return string;
    }

    @Override
    public void saveTelphone(String telphone) throws PersistDataException {
        if (!sharedPreferences.edit().putString(TELPHONE_KEY, telphone).commit()) {
            throw new PersistDataException("Token 保存失败");
        }
    }

    @Override
    public void clearTelPhone() throws PersistDataException {
        saveTelphone("");
    }

    @Override
    public String getTelphone() throws GetPersistenceDataException {
        try {
            String string = sharedPreferences.getString(TELPHONE_KEY, "");
            return string;
        } catch (ClassCastException e) {
            throw new GetPersistenceDataException(e);
        }
    }

    @Override
    public boolean saveAccount(LoginParam account) throws PersistDataException {
        AccountEntity entity = new AccountEntity();
        entity.setAccount(account.getAccount());
        entity.setPassword(account.getPassword());
        entity.setPersistedTime(System.currentTimeMillis());
        try {
//            AccountEntity queryForId = accountDao.queryForId(entity.getAccount());
//            if (queryForId == null) {
            int i = accountDao.create(entity);
            return i == 1;
//            } else {
//                int i = accountDao.update(entity);
//                return i == 1;
//            }
        } catch (SQLException e) {
            throw new PersistDataException(e);
        }

    }

    @Override
    public LoginParam getAccount() throws GetPersistenceDataException, InvalidPersistenceDataException {
        QueryBuilder<AccountEntity, String> queryBuilder = accountDao.queryBuilder();
        try {
            PreparedQuery<AccountEntity> prepare = queryBuilder.orderBy("persistedTime", false).prepare();
            List<AccountEntity> query = accountDao.query(prepare);
            if (!query.isEmpty()) {
                AccountEntity entity = query.get(0);
                if (!accountValidator.isValid(entity)) {
                    Date date = new Date(entity.getPersistedTime());
                    throw new InvalidPersistenceDataException(date.toString());
                }
                return new LoginParam(entity.getAccount(), entity.getPassword());
            }
            throw new GetPersistenceDataException();
        } catch (SQLException e) {
            throw new GetPersistenceDataException(e);
        }
    }

    private Account from(@NonNull AccountEntity entity) {
        Account account = new Account();
        account.setAccount(entity.getAccount());
        account.setPassword(entity.getPassword());
        account.setPersistentTime(entity.getPersistedTime());
        return account;
    }


    @Override
    public boolean saveAccount(Account account) throws PersistDataException {
        AccountEntity entity = new AccountEntity();
        entity.setAccount(account.getAccount());
        entity.setPassword(account.getPassword());
        entity.setPersistedTime(System.currentTimeMillis());
        try {
            int i = accountDao.create(entity);
            return i == 1;
        } catch (SQLException e) {
            throw new PersistDataException(e);
        }
    }

    @Override
    public List<Account> getAccounts() throws GetPersistenceDataException {
        QueryBuilder<AccountEntity, String> queryBuilder = accountDao.queryBuilder();
        try {
            PreparedQuery<AccountEntity> prepare = queryBuilder.orderBy("persistedTime", false).prepare();
            List<AccountEntity> query = accountDao.query(prepare);
            List<Account> accounts = new ArrayList<>(query.size());
            for (AccountEntity entity : query) {
                accounts.add(from(entity));
            }
            return accounts;
        } catch (SQLException e) {
            throw new GetPersistenceDataException(e);
        }
    }


    private int deleteEntity(PersonalInfoEntity entity) throws SQLException {
        if (entity != null) {
            return internalDeleteEntity(entity.getAccount());
        }
        return 0;
    }

    private int internalDeleteEntity(String account) throws SQLException {
        DeleteBuilder<PersonalInfoEntity, String> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("account", account);
        return deleteBuilder.delete();
    }
}
