package com.homepaas.sls.data.repository;

import android.util.Log;

import com.homepaas.sls.data.entity.mapper.WorkerServiceTypeMapper;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.dataformat.request.BusinessServiceRequest;
import com.homepaas.sls.data.repository.datasource.BusinessInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.ServiceTypeListNDataSource;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.ServiceContent;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.homepaas.sls.domain.repository.ServiceTypeListRepo;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/4/27.
 */
public class ServiceTypeListRepoImpl implements ServiceTypeListRepo {

    private static final String TAG = "TAG";
    @Inject
    ServiceTypeListNDataSource nDataSource;
    @Inject
    BusinessInfoNDataSource businessInfoNDataSource;
    @Inject
    WorkerServiceTypeMapper mapper;

    @Inject
    public ServiceTypeListRepoImpl() {

    }

    /**
     * 获取工人服务列表
     * @return
     */
    @Override
    public WorkerServiceTypeInfo getWorkerServiceTypeInfo(String id) {

        WorkerServiceTypeInfo workerServiceTypeInfo = null;
        try {
            workerServiceTypeInfo = nDataSource.getWorkerServiceTypeInfo(id);
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workerServiceTypeInfo;
    }

    /**
     * 获取商户服务列表
     * */
    @Override
    public BusinessServiceTypeInfo getBusinessServiceTypeInfo(String id) {
        BusinessServiceTypeInfo businessServiceTypeInfo = null;
        try {
            List<ServiceContent> serviceContentList = businessInfoNDataSource.getServiceContentList(id);
            Log.i(TAG, "getBusinessServiceTypeInfo: "+serviceContentList.get(0).getServiceName());
            businessServiceTypeInfo =  mapper.transform(serviceContentList);

        } catch (GetNetworkDataException e) {
            e.printStackTrace();
        } catch (ResponseMetaDataException e) {
            e.printStackTrace();
        }
        return businessServiceTypeInfo;
    }
}
