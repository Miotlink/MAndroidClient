package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.WorkerTagsNDataSource;
import com.homepaas.sls.data.repository.datasource.impl.WorkerTagsNDataSourceImpl;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.WorkerTagsRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sheirly on 2016/9/8.
 */
@Singleton
public class WorkerTagsRepoImpl implements WorkerTagsRepo {
    @Inject
    WorkerTagsNDataSource  workerTagsNDataSource;

    @Inject
    public WorkerTagsRepoImpl() {
    }

    @Override
    public GetWorkerTagsInfo getWorkerTagsInfo(String workerId) throws GetDataException, AuthException {

        try {
            return workerTagsNDataSource.getWorkerTagsInfo(workerId);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }
}
