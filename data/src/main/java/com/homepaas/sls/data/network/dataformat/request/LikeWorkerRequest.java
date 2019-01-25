package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/1 0001
 *
 * @author zhudongjie .
 */
public class LikeWorkerRequest {
    @SerializedName("Token")
    public final String token;
    @SerializedName("Id")
    public final String id;

    /**
     * 1点赞 2取消点赞
     */
    @SerializedName("Type")
    public final int type;

    public LikeWorkerRequest(String token, String id, int type) {
        this.token = token;
        this.id = id;
        this.type = type;
    }

    public LikeWorkerRequest(String token, String id, boolean like) {
        this.token = token;
        this.id = id;
        this.type = like ? 1 : 2;
    }
}
