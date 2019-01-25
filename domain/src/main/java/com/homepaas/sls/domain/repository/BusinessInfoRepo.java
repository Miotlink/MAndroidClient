package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.BusinessCollectionEntity;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.ServiceContent;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.AuthException;

import java.util.List;

/**
 * on 2016/1/18 0018
 *
 * @author zhudongjie .
 */
public interface BusinessInfoRepo {

    BusinessInfo getBusinessInfo(String businessId) throws GetDataException;

    List<BusinessCollectionEntity> getCollectedBusinessList() throws GetDataException,AuthException;

    boolean likeBusiness(String businessId, boolean like) throws GetDataException,AuthException;

    boolean collectBusiness(String businessId, boolean collect) throws GetDataException,AuthException;

    boolean checkCallable(String businessId,String phone) throws GetDataException;

    List<Evaluation> getEvaluationList(String businessId, int sizeIndex, int sizePage)throws GetDataException;

    List<ServiceContent> getServiceContentList(String businessId)throws GetDataException;

    /**
     *  商户纠错
     * @param businessId 商户Id
     * @return 操作消息
     * @throws GetDataException
     */
    String reportBusiness(String businessId)throws GetDataException;
}
