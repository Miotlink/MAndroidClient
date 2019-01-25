package com.homepaas.sls.domain.entity;

/**
 * Created by CMJ on 2016/6/25.
 * 账户信息，账户余额等信息
 */

public class BalanceInfo {

    public String balance;
    public String balanceLock;
    public String status;

    public BalanceInfo() {
    }

    public BalanceInfo(String balance, String balanceLock, String status) {
        this.balance = balance;
        this.balanceLock = balanceLock;
        this.status = status;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalanceLock() {
        return balanceLock;
    }

    public void setBalanceLock(String balanceLock) {
        this.balanceLock = balanceLock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
