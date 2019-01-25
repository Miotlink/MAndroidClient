package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/6/20 0020
 *
 * @author zhudongjie
 */
public class ReportWorkerRequest {

    @SerializedName("Token")
    public final String token;

    @SerializedName("WorkerId")
    public final String workerId;

    public ReportWorkerRequest(String token, String workerId) {
        this.token = token;
        this.workerId = workerId;
    }
}
