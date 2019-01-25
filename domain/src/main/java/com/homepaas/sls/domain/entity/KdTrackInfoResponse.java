package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2017/6/5.
 */

public class KdTrackInfoResponse {
    //2：在途中，3：已签收，4：问题件,
    @SerializedName("State")
    private String state;
    //物流跟踪信息
    @SerializedName("Traces")
    private List<KdApiSearchResponseTrace> traces;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<KdApiSearchResponseTrace> getTraces() {
        return traces;
    }

    public void setTraces(List<KdApiSearchResponseTrace> traces) {
        this.traces = traces;
    }
}
