package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2017/6/1.
 */

public class OrderCancelReasonEntity {
    @SerializedName("List")
    private List<String> cancelReasonList;

    public List<String> getCancelReasonList() {
        return cancelReasonList;
    }

    public void setCancelReasonList(List<String> cancelReasonList) {
        this.cancelReasonList = cancelReasonList;
    }
}
