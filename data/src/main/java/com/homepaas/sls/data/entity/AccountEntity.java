package com.homepaas.sls.data.entity;

import com.homepaas.sls.data.validator.ttl.TtlObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * on 2016/2/27 0027
 *
 * @author zhudongjie .
 */
@DatabaseTable(tableName = "tb_accountInfo")
public class AccountEntity implements TtlObject {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField()
    private String account;

    @DatabaseField
    private String password;

    @DatabaseField
    private long persistedTime;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPersistedTime(long persistedTime) {
        this.persistedTime = persistedTime;
    }

    @Override
    public long getPersistedTime() {
        return persistedTime;
    }
}
