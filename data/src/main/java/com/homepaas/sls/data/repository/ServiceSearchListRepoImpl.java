package com.homepaas.sls.data.repository;

import android.util.Log;

import com.homepaas.sls.data.entity.mapper.BusinessDataMapper;
import com.homepaas.sls.data.entity.mapper.WorkerDataMapper;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.NoSearchServiceException;
import com.homepaas.sls.data.exception.OutOfSearchServiceException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.BusinessInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.SearchServiceListNDataSource;
import com.homepaas.sls.data.repository.datasource.ServiceSearchListMockDataSource;
import com.homepaas.sls.data.repository.datasource.WorkerInfoPDataSource;
import com.homepaas.sls.domain.entity.ServiceInfo;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.NoServiceException;
import com.homepaas.sls.domain.exception.OutOfServiceException;
import com.homepaas.sls.domain.param.SearchParameter;
import com.homepaas.sls.domain.repository.ServiceSearchListRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/13 0013
 *
 * @author zhudongjie .
 */
@Singleton
public class ServiceSearchListRepoImpl implements ServiceSearchListRepo {

    private static final String TAG = "ServiceSearchListRepo";

    private ServiceSearchListMockDataSource mockDataSource;

    private SearchServiceListNDataSource nDataSource;

    private WorkerInfoPDataSource workerInfoPDataSource;

    private BusinessInfoPDataSource businessInfoPDataSource;

    private PersonalInfoPDataSource infoPDataSource;

    @Inject
    public ServiceSearchListRepoImpl(ServiceSearchListMockDataSource mockDataSource, SearchServiceListNDataSource nDataSource, WorkerInfoPDataSource workerInfoPDataSource, BusinessInfoPDataSource businessInfoPDataSource, BusinessDataMapper businessDataMapper, WorkerDataMapper workerDataMapper, PersonalInfoPDataSource infoPDataSource) {
        this.mockDataSource = mockDataSource;
        this.nDataSource = nDataSource;
        this.workerInfoPDataSource = workerInfoPDataSource;
        this.businessInfoPDataSource = businessInfoPDataSource;
        this.infoPDataSource = infoPDataSource;
    }

    @Override
    public ServiceInfo getServiceInfo(SearchParameter searchParameter) throws GetDataException, OutOfServiceException, NoServiceException {

        ServiceInfo serviceInfo;
        try {
            String token;
            try {
                token = infoPDataSource.getToken();
            } catch (GetPersistenceDataException e) {
                Log.e(TAG, "getServiceInfo: ", e);
                token = "";
            }
            serviceInfo = nDataSource.getService(searchParameter, token);

        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        } catch (OutOfSearchServiceException e) {
           throw new OutOfServiceException(e.getMessage(),e);
        } catch (NoSearchServiceException e) {
            throw new NoServiceException(e.getMessage(),e);
        }
        return serviceInfo;
    }


}
