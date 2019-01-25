package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJJ on 2016/7/14.
 */
public class GetOrderRequest {
    @SerializedName("Token")
    private final String token;
    @SerializedName("PageSize")
    private final String pageSize;
    @SerializedName("PageIndex")
    private final String pageIndex;
    @SerializedName("Type")
    private final String type;

    public GetOrderRequest(String token, String pageSize, String pageIndex, String type) {

        this.token = token;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public String getType() {
        return type;
    }
}
