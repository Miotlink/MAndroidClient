package com.homepaas.sls.domain.entity;

/**
 * login info
 *
 * @author zhudongjie
 */
public class Account implements Comparable<Account> {

    private String account;
    private String password;

    private long persistentTime;

    public Account() {
    }

    public Account(String account, String password) {
        this.account = account;
        this.password = password;
        persistentTime = System.currentTimeMillis();
    }

    public Account(String account) {
        this.account = account;
        persistentTime = System.currentTimeMillis();
    }

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

    public long getPersistentTime() {
        return persistentTime;
    }

    public void setPersistentTime(long persistentTime) {
        this.persistentTime = persistentTime;
    }

    @Override
    public int compareTo(Account o) {
        return (int) (persistentTime - o.persistentTime);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("account='").append(account).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", persistentTime=").append(persistentTime);
        sb.append('}');
        return sb.toString();
    }
}
