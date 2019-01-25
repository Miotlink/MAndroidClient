package com.homepaas.sls.data.repository;

import android.util.Log;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.BusinessInfoMockDataSource;
import com.homepaas.sls.data.repository.datasource.BusinessInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.DeviceInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.BusinessCollectionEntity;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.ServiceContent;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/18 0018
 *
 * @author zhudongjie .
 */
@Singleton
public class BusinessInfoRepoImpl implements BusinessInfoRepo {

    private static final String TAG = "BusinessInfoRepoImpl";

    @Inject
    BusinessInfoMockDataSource businessInfoMockDataSource;

    @Inject
    BusinessInfoNDataSource businessInfoNDataSource;

    @Inject
    DeviceInfoPDataSource devicePDataSource;

    @Inject
    PersonalInfoPDataSource pDataSource;

    @Inject
    public BusinessInfoRepoImpl() {
    }

    @Override
    public BusinessInfo getBusinessInfo(String businessId) throws GetDataException {
        try {
            String token = null;
            try {
                token = pDataSource.getToken();
            } catch (GetPersistenceDataException e) {
                Log.e(TAG, "获取本地token失败", e);
                token = "";
            }
            return businessInfoNDataSource.getBusinessInfo(businessId, token);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }

    }

    @Override
    public List<BusinessCollectionEntity> getCollectedBusinessList() throws GetDataException, AuthException {
        try {
            String token = pDataSource.getToken();
            return businessInfoNDataSource.getCollectedBusinessList(token);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                pDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "likeBusiness: ",e1);
            }
            throw new AuthException(e.getMessage(),e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }
    }

    @Override
    public boolean likeBusiness(String businessId, boolean like) throws GetDataException, AuthException {
        try {
            String token = pDataSource.getToken();
//            if (TextUtils.isEmpty(token))
//                throw new AuthException("未登录");
           return businessInfoNDataSource.likeBusiness(businessId, token, like);
//            return businessInfoNDataSource.getBusinessInfo(businessId, token);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        } catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                pDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "likeBusiness: ",e1);
            }
            throw new AuthException(e.getMessage(),e);
        }
    }

    @Override
    public boolean collectBusiness(String businessId, boolean collect) throws GetDataException, AuthException {
        String token = null;
        try {
             token = pDataSource.getToken();
//            if (TextUtils.isEmpty(token))
//                throw new AuthException("未登录");
            return businessInfoNDataSource.collectBusiness(businessId, token, collect);
//            return businessInfoNDataSource.getBusinessInfo(businessId, token);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        }/* catch (ResponseMetaDataException e) {
            try {
                BusinessInfo businessInfo = businessInfoNDataSource.getBusinessInfo(businessId, token);
                return businessInfo;
            } catch (GetNetworkDataException e1) {
                throw new GetDataException(e1);
            } catch (ResponseMetaDataException e1) {
                throw new GetDataException(e1);
            }
        }*/ catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                pDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "likeBusiness: ",e1);
            }
            throw new AuthException(e.getMessage(),e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e);
        }
    }

    @Override
    public boolean checkCallable(String businessId, String phone) throws GetDataException {
        try {
            String deviceId = devicePDataSource.getDeviceId();
            return businessInfoNDataSource.checkCallable(businessId, phone, deviceId);
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
    public List<Evaluation> getEvaluationList(String businessId, int sizeIndex, int sizePage) throws GetDataException {
        try {
            return  businessInfoNDataSource.getEvaluationList(businessId,sizeIndex,sizePage);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }
    }

    @Override
    public List<ServiceContent> getServiceContentList(String businessId) throws GetDataException {
        try {
            return  businessInfoNDataSource.getServiceContentList(businessId);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }
    }

    @Override
    public String reportBusiness(String businessId) throws GetDataException {
        try {
            String token  = null;
            try {
                token = pDataSource.getToken();
            } catch (GetPersistenceDataException e) {
                Log.e(TAG, "reportBusiness: ",e );
                token  = "";
            }
            return businessInfoNDataSource.reportBusiness(businessId,token);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }

    }

    public BusinessInfo testCollectionBusiness() throws GetDataException {
        BusinessInfo businessInfo = businessInfoMockDataSource.getBusinessInfo("");
        if (businessInfo == null) {
            throw new GetDataException("");
        } else {
            return businessInfo;
        }

    }
}
