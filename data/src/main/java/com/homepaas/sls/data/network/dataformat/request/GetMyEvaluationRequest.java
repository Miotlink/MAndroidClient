package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/7/8 0008
 *
 * @author zhudongjie
 */
public class GetMyEvaluationRequest {
    @SerializedName("Token")
    public final String token;
    @SerializedName("PageIndex")
    public final int pageIndex;
    @SerializedName("PageSize")
    public final int pageSize;

    public GetMyEvaluationRequest(String token, int pageIndex, int pageSize) {
        this.token = token;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
