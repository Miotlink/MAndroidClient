package com.homepaas.sls.data.entity.mapper;

import com.homepaas.sls.data.entity.CallLogEntity;
import com.homepaas.sls.domain.entity.CallLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
@Singleton
public class CallLogEntityMapper {

    @Inject
    public CallLogEntityMapper() {
    }

    public CallLog transform(CallLogEntity entity) {
        CallLog callLog = null;
        if (entity != null) {
            callLog = new CallLog();
            callLog.setId(entity.getAccountId());
            callLog.setAttribution(entity.getAttribution());
            callLog.setDialled(entity.getIsDialled() == 0);
            callLog.setName(entity.getName());
            callLog.setPhoneNumber(entity.getPhoneNumber());
            callLog.setTime(entity.getTime());
            callLog.setType(entity.getType());
            callLog.setPhotoUrl(entity.getPhotoUrl());
            callLog.setDuration(entity.getDuration());
        }
        return callLog;
    }

    public List<CallLog> transform(List<CallLogEntity> entities) {
        List<CallLog> callLogs = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            callLogs.add(transform(entities.get(i)));
        }
        return callLogs;
    }

    public CallLogEntity transform(CallLog callLog) {
        CallLogEntity entity = null;
        if (callLog != null) {
            entity = new CallLogEntity();
            entity.setAccountId(callLog.getId());
            entity.setType(callLog.getType());
            entity.setTime(callLog.getTime());
            entity.setPhoneNumber(callLog.getPhoneNumber());
            entity.setAttribution(callLog.getAttribution());
            entity.setIsDialled(callLog.isDialled() ? 0 : 1);
            entity.setName(callLog.getName());
            entity.setPhotoUrl(callLog.getPhotoUrl());
            entity.setDuration(callLog.getDuration());
        }
        return entity;
    }
}
