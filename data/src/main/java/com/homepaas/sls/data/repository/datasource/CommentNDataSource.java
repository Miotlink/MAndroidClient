package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.param.EvaluateParam;

import java.util.List;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public interface CommentNDataSource {

    List<Evaluation> getMyEvaluationList(String token,int pageIndex,int pageSize)throws GetNetworkDataException,ResponseMetaAuthException,ResponseMetaDataException;

    List<Evaluation> getWorkerEvaluationList(String workerId,int pageIndex,int pageSize)throws GetNetworkDataException,ResponseMetaDataException;

    List<Evaluation> getBusinessEvaluationList(String businessId,int pageIndex,int pageSize)throws GetNetworkDataException,ResponseMetaDataException;

    String addEvaluation(String token, EvaluateParam param)throws GetNetworkDataException,ResponseMetaAuthException,ResponseMetaDataException;

    String evaluateOrder(String token,EvaluateParam param)throws GetNetworkDataException,ResponseMetaAuthException,ResponseMetaDataException;
}
