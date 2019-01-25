package com.homepaas.sls.domain.entity;

import java.util.List;

/**
 * Created by JWC on 2017/7/4.
 * 后台不传！！！！！！！！！！！！
 * 赔付原因
 */

public class ReasonsEntity {
    String reason;   //理由
    List<String> lateTime;  //迟到时间
    String reasonTime;
    //0：工人迟到  1：工人爽约
    String id;

    public String getReasonTime() {
        return reasonTime;
    }

    public void setReasonTime(String reasonTime) {
        this.reasonTime = reasonTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getLateTime() {
        return lateTime;
    }

    public void setLateTime(List<String> lateTime) {
        this.lateTime = lateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
