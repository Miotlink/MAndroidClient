package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/7/6.
 */

public class SubmitApplyClaimsRequest {
    /**
     * Token：必填；
     OrderId：必填；
     ReasonType：原因类型，必填；0:工人迟到，1：工人爽约;
     WorkerLaterTime 工人迟到的时间段：ReasonType为0时，才必填;
     0：20分钟以内，1：20-40分钟，2：40分钟以上
     Reason：原因描述，必填；
     */
    @SerializedName("Token")
    private String token;
    @SerializedName("OrderId")
    private String orderId;
    @SerializedName("ReasonType")
    private String reasonType;
    @SerializedName("WorkerLaterTime")
    private String workerLaterTime;
    @SerializedName("Reason")
    private String Reason;

    public SubmitApplyClaimsRequest(String token, String orderId, String reasonType, String workerLaterTime, String reason) {
        this.token = token;
        this.orderId = orderId;
        this.reasonType = reasonType;
        this.workerLaterTime = workerLaterTime;
        Reason = reason;
    }
}
