package com.homepaas.sls.data.repository.datasource.impl;

import android.util.Log;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.EvaluationBody;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.AddEvaluationRequest;
import com.homepaas.sls.data.network.dataformat.request.EvaluateOrderRequest;
import com.homepaas.sls.data.network.dataformat.request.EvaluationListRequest;
import com.homepaas.sls.data.network.dataformat.request.GetMyEvaluationRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.CommentNDataSource;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.param.EvaluateParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
@Singleton
public class CommentNDataSourceImpl implements CommentNDataSource {

    private static final String TAG = "CommentNDataSourceImpl";

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public CommentNDataSourceImpl() {
    }

    @Override
    public List<Evaluation> getMyEvaluationList(String token,int pageIndex,int pageSize) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        GetMyEvaluationRequest request = new GetMyEvaluationRequest(token,pageIndex,pageSize);
        try {
            Response<ResponseWrapper<EvaluationBody>> response = apiHelper.getEvaluations(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.getEvaluations();
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public List<Evaluation> getWorkerEvaluationList(String workerId,int pageIndex,int pageSize) throws GetNetworkDataException, ResponseMetaDataException {
        EvaluationListRequest request = new EvaluationListRequest(workerId, pageIndex, pageSize);
        try {
            Response<ResponseWrapper<EvaluationBody>> response = apiHelper.getWorkerEvaluations(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.getEvaluations();
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //server doesn't check auth ,it should never happen
            Log.e(TAG, "getEvaluationList: ", e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Evaluation> getBusinessEvaluationList(String businessId,int pageIndex,int pageSize) throws GetNetworkDataException, ResponseMetaDataException {
        EvaluationListRequest request = new EvaluationListRequest(businessId, pageIndex, pageSize);
        try {
            Response<ResponseWrapper<EvaluationBody>> response = apiHelper.getBusinessEvaluations(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.getEvaluations();
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //server doesn't check auth ,it should never happen
            Log.e(TAG, "getEvaluationList: ", e);
        }
        return new ArrayList<>();
    }

    @Override
    public String addEvaluation(String token, EvaluateParam param) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        AddEvaluationRequest request = new AddEvaluationRequest();
        request.setToken(token);
        request.setOwnerId(param.id);
        request.setContent(param.content);
        request.setOrderId(param.orderId);
        request.setOwnerType(param.type);
        request.setScore(param.score);
        try {

            Response<ResponseWrapper<Void>> response = apiHelper.addEvaluation(request, param.pictures);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().meta.getErrorMsg();
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }

    }

    @Override
    public String evaluateOrder(String token, EvaluateParam param) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        EvaluateOrderRequest request = new EvaluateOrderRequest(token,param.orderId,param.score,param.content);
        try {

            Response<ResponseWrapper<Void>> response = apiHelper.evaluateOrder(request, param.pictures);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().meta.getErrorMsg();
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }

    }
}
