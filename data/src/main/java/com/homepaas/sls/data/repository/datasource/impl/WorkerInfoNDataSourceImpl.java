package com.homepaas.sls.data.repository.datasource.impl;

import android.util.Log;

import com.homepaas.sls.data.entity.WorkerEntity;
import com.homepaas.sls.data.entity.mapper.WorkerDataMapper;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.CollectedWorkerBody;
import com.homepaas.sls.data.network.dataformat.EvaluationBody;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ProviderDetailBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.WorkerInfoBody;
import com.homepaas.sls.data.network.dataformat.request.CheckCallableRequest;
import com.homepaas.sls.data.network.dataformat.request.CollectWorkerRequest;
import com.homepaas.sls.data.network.dataformat.request.EvaluationListRequest;
import com.homepaas.sls.data.network.dataformat.request.LikeWorkerRequest;
import com.homepaas.sls.data.network.dataformat.request.ProviderDetailRequest;
import com.homepaas.sls.data.network.dataformat.request.ReportWorkerRequest;
import com.homepaas.sls.data.network.dataformat.request.WorkerInfoRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.WorkerInfoNDataSource;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.WorkerCollectionEntity;
import com.homepaas.sls.domain.entity.WorkerInfo;

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
public class WorkerInfoNDataSourceImpl implements WorkerInfoNDataSource {

    private static final String TAG = "WorkerInfoNDataSource";

    private PersonalInfoPDataSource pDataSource;

    private RestApiHelper apiHelper;

    private WorkerDataMapper workerDataMapper;

    @Inject
    public WorkerInfoNDataSourceImpl(PersonalInfoPDataSource pDataSource,
                                     RestApiHelper apiHelper, WorkerDataMapper workerDataMapper) {
        this.pDataSource = pDataSource;
        this.apiHelper = apiHelper;
        this.workerDataMapper = workerDataMapper;
    }

    @Override
    public WorkerInfo getWorkerInfo(String workerId, String token) throws GetNetworkDataException, ResponseMetaDataException {
        try {
            ProviderDetailRequest request = new ProviderDetailRequest("1",workerId,token);
            Response<ResponseWrapper<ProviderDetailBody>> response = apiHelper.getProviderDetailInfo(request);
//            WorkerInfoRequest request = new WorkerInfoRequest(workerId, token);
//            Response<ResponseWrapper<WorkerInfoBody>> response = apiHelper.getWorkerInfo(request);

            if (response.code() == 200) {
                if (response.body().meta.isValid()) {
                    WorkerEntity workerEntity = response.body().data.getWorkerEntity();
                    return workerDataMapper.transform(workerEntity);
                } else {
                    throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
                }

            } else {
                throw new GetNetworkDataException("Wrong response code:" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            //获取工人信息可以不登录，不应该出现此异常
            Log.e(TAG, "getWorkerInfo: ", e);
            return null;
        }
    }

    @Override
    public boolean likeWorker(String workerId, String token, boolean like) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        LikeWorkerRequest request = new LikeWorkerRequest(token, workerId, like);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.likeWorker(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return true;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误代码：" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public boolean collectWorker(String workerId, String token, boolean collect) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        CollectWorkerRequest request = new CollectWorkerRequest(token, workerId, collect);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.collectWorker(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return true;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误代码：" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public List<WorkerCollectionEntity> getCollectedWorkerList(String token) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        try {
            Response<ResponseWrapper<CollectedWorkerBody>> response = apiHelper.getCollectedWorkerList(token);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.getCollectedWorkers();
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            } else {
                throw new GetNetworkDataException("错误代码：" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }

    }

    @Override
    public boolean checkCallable(String workerId, String phone, String deviceId) throws GetNetworkDataException, ResponseMetaDataException {
        CheckCallableRequest request = new CheckCallableRequest(1, workerId, phone, deviceId);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.checkCallable(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return true;
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new GetNetworkDataException("错误响应码：" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        } catch (ResponseMetaAuthException e) {
            Log.e(TAG, "checkCallable: ", e);
            return false;
        }
    }

    @Override
    public List<Evaluation> getEvaluationList(String id, int pageIndex, int pageSize) throws GetNetworkDataException, ResponseMetaDataException {
        EvaluationListRequest request = new EvaluationListRequest(id, pageIndex, pageSize);
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
    public String reportWorker(String id, String token) throws GetNetworkDataException, ResponseMetaDataException {
        ReportWorkerRequest request = new ReportWorkerRequest(token, id);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.reportWorker(request);
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
            Log.e(TAG, "reportWorker: ", e);
        }
        return null;
    }
}
