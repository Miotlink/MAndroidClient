package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/1/29 0029
 *
 * @author zhudongjie .
 */
public class WorkerInfoRequest {
    @SerializedName("WorkerId")
    public final String workerId;
    @SerializedName("Token")
    public final String token;

    public WorkerInfoRequest(String workerId, String token) {
        this.workerId = workerId;
        this.token = token;
    }
}
