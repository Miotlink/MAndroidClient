package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * account message
 *
 * @author zhudongjie
 */
public class AccountInfo {


    @SerializedName("Balance")
    private String enabledBalance;

    @SerializedName("BalanceLock")
    private String disabledBalance;

    @SerializedName("Status")
    private String status;

    public String getEnabledBalance() {
        return enabledBalance;
    }

    public void setEnabledBalance(String enabledBalance) {
        this.enabledBalance = enabledBalance;
    }

    public String getDisabledBalance() {
        return disabledBalance;
    }

    public void setDisabledBalance(String disabledBalance) {
        this.disabledBalance = disabledBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
