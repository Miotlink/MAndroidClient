package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.domain.entity.CallLog;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.SaveDataException;

import java.util.List;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
public interface CallLogPDataSource {

    List<CallLog> getCallLogs() throws GetDataException;

    int deleteCallLogs(String phoneNumber) throws SaveDataException;

    List<CallLog> getCallLogs(String phoneNumber) throws GetDataException;

    boolean saveCallLog(CallLog callLog )throws SaveDataException;
}
