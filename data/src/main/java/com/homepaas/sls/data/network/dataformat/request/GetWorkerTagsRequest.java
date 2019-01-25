package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sherily on 2016/9/8.
 */
public class GetWorkerTagsRequest {
    @SerializedName("WorkerId")
    public final String workerId;

    public GetWorkerTagsRequest(String workerId) {
        this.workerId = workerId;
    }
}
