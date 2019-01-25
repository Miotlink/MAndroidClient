package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2016/12/5.
 */

public class SettlementListRequest {
    @SerializedName("Token")
    private String token;
    @SerializedName("IsMinus")
    private String isMinus;
    @SerializedName("PageIndex")
    private int pageIndex;
    @SerializedName("PageSize")
    private int pageSize;

    public SettlementListRequest(String token, String isMinus, int pageIndex, int pageSize) {
        this.token = token;
        this.isMinus = isMinus;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;

    }


    public String getIsMinus() {
        return isMinus;
    }

    public void setIsMinus(String isMinus) {
        this.isMinus = isMinus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
