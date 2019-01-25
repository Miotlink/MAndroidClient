package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/26 0026
 *
 * @author zhudongjie .
 */
public class CallLogRequest {

    @SerializedName("Id")
    private String id;
    @SerializedName("ServiceTypeId")
    private String serviceTypeId;


    //1工人，2商户
    @SerializedName("CallType")
    private int callType;
    //Phone 拨打的电话，必填
    @SerializedName("Phone")
    private String phone;

    //1yes,0no  IsDial 是否接通，必填
    @SerializedName("IsDial")
    private int isDial;
    //CallTime 通话时间  秒数，必填
    @SerializedName("CallTime")
    private String callTime;

    @SerializedName("Token")
    private String token;

    @SerializedName("DeviceId")
    private String deviceId;
    @SerializedName("Reason")
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsDial() {
        return isDial;
    }

    public void setIsDial(int isDial) {
        this.isDial = isDial;
    }

    public void setIsDial(boolean isDial) {
        this.isDial = isDial ? 1 : 0;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public CallLogRequest() {
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public CallLogRequest(String id, int callType, String phone, int isDial, String callTime, String token, String deviceId, String reason) {
        this.id = id;
        this.callType = callType;
        this.phone = phone;
        this.isDial = isDial;
        this.callTime = callTime;
        this.token = token;
        this.deviceId = deviceId;
        this.reason = reason;
    }
}
