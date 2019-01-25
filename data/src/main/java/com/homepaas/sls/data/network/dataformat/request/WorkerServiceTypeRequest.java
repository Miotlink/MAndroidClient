package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CMJ on 2016/6/21.
 */

public class WorkerServiceTypeRequest {

    @SerializedName("WorkerId")
    String id;

    public WorkerServiceTypeRequest(String id) {
        this.id = id;
    }

    public WorkerServiceTypeRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
