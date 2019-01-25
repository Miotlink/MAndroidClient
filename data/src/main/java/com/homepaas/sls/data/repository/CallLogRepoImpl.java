package com.homepaas.sls.data.repository;

import android.util.Log;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.CallLogNDataSource;
import com.homepaas.sls.data.repository.datasource.CallLogPDataSource;
import com.homepaas.sls.data.repository.datasource.DeviceInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.CallLog;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.SaveDataException;
import com.homepaas.sls.domain.repository.CallLogRepo;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
@Singleton
public class CallLogRepoImpl implements CallLogRepo {

    private static final String TAG = "CallLogRepo";

    private CallLogPDataSource callLogPDataSource;

    private CallLogNDataSource callLogNDataSource;

    private PersonalInfoPDataSource personalInfoPDataSource;

    private DeviceInfoPDataSource deviceInfoPDataSource;

    @Inject
    public CallLogRepoImpl(CallLogPDataSource callLogPDataSource, CallLogNDataSource callLogNDataSource,
                           PersonalInfoPDataSource personalInfoPDataSource, DeviceInfoPDataSource deviceInfoPDataSource) {
        this.callLogPDataSource = callLogPDataSource;
        this.callLogNDataSource = callLogNDataSource;
        this.personalInfoPDataSource = personalInfoPDataSource;
        this.deviceInfoPDataSource = deviceInfoPDataSource;
    }

    @Override
    public List<CallLog> getCallLogs() throws GetDataException {
        return callLogPDataSource.getCallLogs();
    }

    @Override
    public int deleteCallLogs(String phoneNumber) throws SaveDataException {
        return callLogPDataSource.deleteCallLogs(phoneNumber);
    }

    @Override
    public List<CallLog> getCallLogs(String phoneNumber) throws GetDataException {
        return callLogPDataSource.getCallLogs(phoneNumber);
    }

    @Override
    public boolean saveCallLog(CallLog callLog) throws SaveDataException, AuthException {
        boolean result =  callLogPDataSource.saveCallLog(callLog);
        if (result){
            //上传通话记录
            String token = "";
            try {
                token = personalInfoPDataSource.getToken();
            } catch (GetPersistenceDataException e) {
                Log.e(TAG, "saveCallLog: ", e);
            }
            try {
                String deviceId = deviceInfoPDataSource.getDeviceId();
                callLogNDataSource.uploadCallLog(callLog,token,deviceId);
            } catch ( GetNetworkDataException | ResponseMetaDataException e) {
                Log.e(TAG, "saveCallLog: ",e );

            } catch (ResponseMetaAuthException | GetPersistenceDataException e) {

                throw new AuthException(e.getMessage(),e);
            }

        }
        return result;
    }
}
