package com.homepaas.sls.data.repository.datasource.impl;

import android.util.Log;

import com.homepaas.sls.data.entity.BusinessEntity;
import com.homepaas.sls.data.entity.mapper.BusinessDataMapper;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.BusinessInfoBody;
import com.homepaas.sls.data.network.dataformat.CollectedBusinessBody;
import com.homepaas.sls.data.network.dataformat.EvaluationBody;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ProviderDetailBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.ServiceContentBody;
import com.homepaas.sls.data.network.dataformat.request.BusinessInfoRequest;
import com.homepaas.sls.data.network.dataformat.request.BusinessServiceRequest;
import com.homepaas.sls.data.network.dataformat.request.CheckCallableRequest;
import com.homepaas.sls.data.network.dataformat.request.CollectBusinessRequest;
import com.homepaas.sls.data.network.dataformat.request.EvaluationListRequest;
import com.homepaas.sls.data.network.dataformat.request.LikeBusinessRequest;
import com.homepaas.sls.data.network.dataformat.request.ProviderDetailRequest;
import com.homepaas.sls.data.network.dataformat.request.ReportBusinessRequest;
import com.homepaas.sls.data.repository.datasource.BusinessInfoNDataSource;
import com.homepaas.sls.domain.entity.BusinessCollectionEntity;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.ServiceContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * on 2016/1/29 0029
 *
 * @author zhudongjie .
 */
@Singleton
public class BusinessInfoNDataSourceImpl implements BusinessInfoNDataSource {

    private static final String TAG = "BusinessInfoNDataSource";

    @Inject
    RestApiHelper apiHelper;

    @Inject
    BusinessDataMapper businessDataMapper;

    @Inject
    public BusinessInfoNDataSourceImpl() {
    }

    @Override
    public BusinessInfo getBusinessInfo(String businessId, String token) throws GetNetworkDataException, ResponseMetaDataException {
        try {
            ProviderDetailRequest request = new ProviderDetailRequest("2",businessId,token);
            Response<ResponseWrapper<ProviderDetailBody>> response = apiHelper.getProviderDetailInfo(request);
//            BusinessInfoRequest request = new BusinessInfoRequest(businessId, token);
//            Response<ResponseWrapper<BusinessInfoBody>> response = apiHelper.getBusinessInfo(request);
            if (response.code() == 200) {
                if (response.body().meta.isValid()) {
                    BusinessEntity businessEntity = response.body().data.getBusinessEntity();
                    return businessDataMapper.transform(businessEntity);
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码:" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            Log.e(TAG, "getBusinessInfo: ", e);
            //不应有此异常
            return null;
        }
    }

    @Override
    public boolean likeBusiness(String businessId, String token, boolean like) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        LikeBusinessRequest request = new LikeBusinessRequest(token, businessId, like);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.likeBusiness(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return true;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码:" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public boolean collectBusiness(String businessId, String token, boolean collect) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        CollectBusinessRequest request = new CollectBusinessRequest(token, businessId, collect);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.collectBusiness(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return true;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码:" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public List<BusinessCollectionEntity> getCollectedBusinessList(String token) throws GetNetworkDataException, GetPersistenceDataException, ResponseMetaAuthException, ResponseMetaDataException {
        try {
            Response<ResponseWrapper<CollectedBusinessBody>> response = apiHelper.getCollectedBusinessList(token);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
//                    List<BusinessEntity> businesses = response.body().data.getCollectedBusinesses();
                    return response.body().data.getCollectedBusinesses();
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误响应码:" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }

    }

    @Override
    public boolean checkCallable(String businessId, String phone, String deviceId) throws GetNetworkDataException, ResponseMetaDataException {
        CheckCallableRequest request = new CheckCallableRequest(2, businessId, phone, deviceId);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.checkCallable(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()){
                    return true;
                }else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //impossible
            Log.e(TAG, "checkCallable: ", e);
            return false;
        }
    }

    @Override
    public List<Evaluation> getEvaluationList(String id,int pageIndex,int pageSize) throws GetNetworkDataException, ResponseMetaDataException {
        EvaluationListRequest request = new EvaluationListRequest(id, pageIndex, pageSize);
        try {
            Response<ResponseWrapper<EvaluationBody>> response = apiHelper.getBusinessEvaluations(request);
            if (response.code()==200){
                Meta meta = response.body().meta;
                if (meta.isValid()){
                    return response.body().data.getEvaluations();
                }else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //server doesn't check auth ,it should never happen
            Log.e(TAG, "getEvaluationList: ",e );
        }
        return new ArrayList<>();
    }

    @Override
    public List<ServiceContent> getServiceContentList(String id) throws GetNetworkDataException, ResponseMetaDataException {
        BusinessServiceRequest request = new BusinessServiceRequest(id);
        try {
            Response<ResponseWrapper<ServiceContentBody>> response = apiHelper.getBusinessServiceContents(request);
            if (response.code()==200){
                Meta meta = response.body().meta;
                if (meta.isValid()){
                    return response.body().data.serviceContents;
                }else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //server doesn't check auth ,it should never happen
            Log.e(TAG, "getServiceContentList: ", e);
        }
        return new ArrayList<>();
    }

    @Override
    public String reportBusiness(String id,String token) throws GetNetworkDataException, ResponseMetaDataException {
        ReportBusinessRequest request = new ReportBusinessRequest(token, id);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.reportBusiness(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return meta.getErrorMsg();
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //token is nullable ,it should never happen
            Log.e(TAG, "reportBusiness: ", e);
        }
        return null;
    }
}
