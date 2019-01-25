package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.param.EvaluateParam;

import java.util.List;


public interface CommentRepo {

    List<Evaluation> getMyEvaluations(int pageIndex,int pageSize)throws GetDataException, AuthException;

    List<Evaluation> getWorkerEvaluations(String workerId,int pageIndex,int pageSize)throws GetDataException;

    List<Evaluation> getBusinessEvaluations(String businessId,int pageIndex,int pageSize)throws GetDataException;

    /**
     * 评价工人/商户
     */
    String evaluate(EvaluateParam param)throws GetDataException,AuthException;

    String evaluateOrder(EvaluateParam param)throws GetDataException,AuthException;
}
