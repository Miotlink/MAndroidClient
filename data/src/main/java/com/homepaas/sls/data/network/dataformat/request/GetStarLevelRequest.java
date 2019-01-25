package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/7/21.
 */

public class GetStarLevelRequest {
    @SerializedName("ServiceTypeId")
    private String serviceTypeId;

    public GetStarLevelRequest(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
}
