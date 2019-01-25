package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/6/17 0017
 *
 * @author zhudongjie
 */
public class EvaluationListRequest {

    @SerializedName("ID")
    public final String id;
    @SerializedName("PageIndex")
    public final int pageIndex;
    @SerializedName("PageSize")
    public final int pageSize;

    public EvaluationListRequest(String id, int pageIndex, int pageSize) {
        this.id = id;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
