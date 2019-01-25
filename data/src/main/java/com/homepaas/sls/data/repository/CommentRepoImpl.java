package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.CommentNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.param.EvaluateParam;
import com.homepaas.sls.domain.repository.CommentRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
@Singleton
public class CommentRepoImpl implements CommentRepo {

    @Inject
    CommentNDataSource nDataSource;

    @Inject
    PersonalInfoPDataSource pDataSource;

    @Inject
    public CommentRepoImpl() {
    }

    @Override
    public List<Evaluation> getMyEvaluations(int pageIndex,int pageSize) throws GetDataException ,AuthException{
        try {
            String token  = pDataSource.getToken();
            return  nDataSource.getMyEvaluationList(token,pageIndex,pageSize);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        } catch (GetPersistenceDataException |ResponseMetaAuthException e) {
           throw new AuthException(e.getMessage(),e);
        }
    }

    @Override
    public List<Evaluation> getWorkerEvaluations(String workerId, int pageIndex, int pageSize) throws GetDataException {
        try {
            return  nDataSource.getWorkerEvaluationList(workerId,pageIndex,pageSize);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }
    }

    @Override
    public List<Evaluation> getBusinessEvaluations(String businessId, int pageIndex, int pageSize) throws GetDataException {
        try {
            return  nDataSource.getBusinessEvaluationList(businessId,pageIndex,pageSize);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }
    }

    @Override
    public String evaluate(EvaluateParam param) throws GetDataException, AuthException {
        try {
            String token  = pDataSource.getToken();
            return nDataSource.addEvaluation(token,param);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        } catch (GetPersistenceDataException |ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(),e);
        }
    }

    @Override
    public String evaluateOrder(EvaluateParam param) throws GetDataException, AuthException {
        try {
            String token  = pDataSource.getToken();
            return nDataSource.evaluateOrder(token,param);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        } catch (GetPersistenceDataException |ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(),e);
        }
    }
}
