package com.homepaas.sls.data.repository;

import android.text.TextUtils;
import android.util.Log;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.DeviceInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.WorkerInfoMockDataSource;
import com.homepaas.sls.data.repository.datasource.WorkerInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.WorkerInfoPDataSource;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.WorkerCollectionEntity;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/19 0019
 *
 * @author zhudongjie .
 */
@Singleton
public class WorkerInfoRepoImpl implements WorkerInfoRepo {

    private static final String TAG = "WorkerInfoRepo";

    @Inject
    WorkerInfoMockDataSource workerInfoMockDataSource;

    @Inject
    WorkerInfoNDataSource workerInfoNDataSource;

    @Inject
    WorkerInfoPDataSource workerInfoPDataSource;

    @Inject
    DeviceInfoPDataSource deviceInfoPDataSource;

    @Inject
    PersonalInfoPDataSource pDataSource;

    @Inject
    public WorkerInfoRepoImpl() {
    }


    @Override
    public WorkerInfo getWorkerInfo(String workerId) throws GetDataException {
        try {
            String token = null;
            try {
                token = pDataSource.getToken();
            } catch (GetPersistenceDataException e) {
                token  ="";
                Log.e(TAG, "getWorkerInfo: ",e );
            }
            return workerInfoNDataSource.getWorkerInfo(workerId,token);
        } catch (GetNetworkDataException  e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        }  catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }
    }

    @Override
    public List<WorkerCollectionEntity> getCollectedWorkerList() throws GetDataException, AuthException {
        try {
            String token  = pDataSource.getToken();
            return workerInfoNDataSource.getCollectedWorkerList(token);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        } catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                pDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "getCollectedWorkerList: ",e1);
            }
            throw new AuthException(e.getMessage(),e);
        }
    }

    @Override
    public boolean likeWorker(String workerId, boolean like) throws GetDataException, AuthException {
        try {
            String token = pDataSource.getToken();
//            if (TextUtils.isEmpty(token))
//                throw new AuthException("未登录");
           return workerInfoNDataSource.likeWorker(workerId, token, like);
//            WorkerInfo workerInfo;
////            try {
////                 workerInfo = workerInfoPDataSource.get(workerId);
////            } catch (InvalidPersistenceDataException e) {
//            workerInfo = workerInfoNDataSource.getWorkerInfo(workerId,token);
////                workerInfoPDataSource.save();
////            }
//            return workerInfo;
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        } catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                pDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "likeWorker: ",e1);
            }
            throw new AuthException(e.getMessage(),e);
        }
    }

    @Override
    public boolean collectWorker(String workerId, boolean collect) throws GetDataException, AuthException {
        try {
            String token = pDataSource.getToken();
            if (TextUtils.isEmpty(token))
                throw new AuthException("未登录");
//            WorkerInfo workerInfo;
          return workerInfoNDataSource.collectWorker(workerId, token, collect);
//            workerInfo = workerInfoNDataSource.getWorkerInfo(workerId,token);
//            return workerInfo;
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        }  catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            try {
                pDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "collectWorker: ",e1);
            }
            throw new AuthException(e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }
    }

    @Override
    public boolean checkCallable(String workerId, String phone) throws GetDataException {
        try {
            String deviceId = deviceInfoPDataSource.getDeviceId();
            return workerInfoNDataSource.checkCallable(workerId, phone, deviceId);
        } catch (GetPersistenceDataException e) {
            //设备号获取失败，如何处理？？？
            Log.e(TAG, "checkCallable: ", e);
            return false;
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }
    }

    @Override
    public List<Evaluation> getEvaluationList(String workerId, int pageIndex, int pageSize) throws GetDataException {
        try {
            return  workerInfoNDataSource.getEvaluationList(workerId,pageIndex ,pageSize );
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }
    }

    @Override
    public String reportWorker(String workerId) throws GetDataException {
        try {
            String token  = null;
            try {
                token = pDataSource.getToken();
            } catch (GetPersistenceDataException e) {
                Log.e(TAG, "reportWorker: ",e );
                token  = "";
            }
            return workerInfoNDataSource.reportWorker(workerId,token);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }

    }
}
