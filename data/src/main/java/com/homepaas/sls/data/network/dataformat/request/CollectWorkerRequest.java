package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/1 0001
 *
 * @author zhudongjie .
 */
public class CollectWorkerRequest {
    @SerializedName("Token")
    public final String token;
    @SerializedName("Id")
    public final String id;

    /**
     * 1 收藏 ，2 取消收藏
     */
    @SerializedName("Type")
    public final int type;

    public CollectWorkerRequest(String token, String id, int type) {
        this.token = token;
        this.id = id;
        this.type = type;
    }

    public CollectWorkerRequest(String token, String id, boolean collect) {
        this.token = token;
        this.id = id;
        this.type = collect ? 1 : 2;
    }
}
