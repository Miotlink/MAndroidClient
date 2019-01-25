package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.BusinessCollectionEntity;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.ServiceContent;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.exception.GetDataException;

import java.util.List;

/**
 * on 2016/1/29 0029
 *
 * @author zhudongjie .
 */
public interface BusinessInfoNDataSource {

    BusinessInfo getBusinessInfo(String businessId,String token) throws GetNetworkDataException, ResponseMetaDataException;

    boolean likeBusiness(String businessId,String token, boolean like) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;

    boolean collectBusiness(String businessId,String token, boolean collect) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;

    List<BusinessCollectionEntity> getCollectedBusinessList(String token) throws GetNetworkDataException, GetPersistenceDataException, ResponseMetaAuthException, ResponseMetaDataException;

    boolean checkCallable(String businessId,String phone,String deviceId) throws GetNetworkDataException, ResponseMetaDataException;

    List<Evaluation> getEvaluationList(String id,int pageIndex,int pageSize)throws GetNetworkDataException,ResponseMetaDataException;

    List<ServiceContent> getServiceContentList(String id)throws GetNetworkDataException,ResponseMetaDataException;

    String reportBusiness(String id,String token)throws GetNetworkDataException,ResponseMetaDataException;
}
