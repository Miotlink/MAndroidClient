package com.homepaas.sls.mvp.model.mapper;


import android.support.v4.util.ArrayMap;

import com.homepaas.sls.domain.entity.CallLog;
import com.homepaas.sls.mvp.model.CallLogModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
@Singleton
public class CallLogModelMapper {

    //从大到小排列
    private Comparator<CallLogModel> callLogModelComparator = new Comparator<CallLogModel>() {
        @Override
        public int compare(CallLogModel lhs, CallLogModel rhs) {
            return rhs.getTime().compareTo(lhs.getTime());
        }
    };

    @Inject
    public CallLogModelMapper() {
        //empty constructor
    }

    public List<CallLogModel> transform(List<CallLog> callLogs) {
        ArrayMap<String, CallLogModel> map = new ArrayMap<>();
        if (callLogs != null) {
            for (CallLog callLog : callLogs) {
                String key = callLog.getPhoneNumber();
                if (map.containsKey(key)) {
                    updateModel(map.get(key), callLog);
                } else {
                    map.put(key, createModel(callLog));
                }
            }
        }

        List<CallLogModel> callLogModels = new ArrayList<>(map.values());

        Collections.sort(callLogModels, callLogModelComparator);

        return callLogModels;
    }

    private CallLogModel createModel(CallLog callLog) {
        CallLogModel callLogModel = new CallLogModel();
        callLogModel.setName(callLog.getName());
        callLogModel.setAttribution(callLog.getAttribution());
        callLogModel.setPhoneNumber(callLog.getPhoneNumber());
        callLogModel.setType(callLog.getType());
        callLogModel.setCount(1);
        callLogModel.setTime(callLog.getTime());
        callLogModel.setPhotoUrl(callLog.getPhotoUrl());
        callLogModel.setDialled(callLog.isDialled());
        callLogModel.setId(callLog.getId());
        return callLogModel;
    }

    private void updateModel(CallLogModel oldValue, CallLog callLog) {
        oldValue.setCount(oldValue.getCount() + 1);
        if (oldValue.getTime().compareTo(callLog.getTime()) <= 0) {
            oldValue.setTime(callLog.getTime());
        }
    }

}
