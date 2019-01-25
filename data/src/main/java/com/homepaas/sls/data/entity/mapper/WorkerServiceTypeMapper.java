package com.homepaas.sls.data.entity.mapper;

import android.support.annotation.Nullable;
import android.util.Log;

import com.homepaas.sls.data.entity.WorkerServiceTypeEntity;
import com.homepaas.sls.data.network.dataformat.WorkerServiceTypeBody;
import com.homepaas.sls.domain.entity.BusinessServiceType;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.ServiceContent;
import com.homepaas.sls.domain.entity.WorkerServiceType;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/4/28.
 */
@Singleton
public class WorkerServiceTypeMapper {
    private static final String TAG = "WorkerServiceTypeMapper";

    @Inject
    public WorkerServiceTypeMapper() {
    }


    @Nullable
    public WorkerServiceTypeInfo transform(@Nullable WorkerServiceTypeBody body) {

        WorkerServiceTypeInfo workerServiceTypeInfo = new WorkerServiceTypeInfo();

        List<WorkerServiceType> list = new ArrayList<>();
        int size = body.getList().size();
        Log.i(TAG, "transform: "+size);
        List<WorkerServiceTypeEntity> entityList = body.getList();
        for (int i  =0 ;i<size;i++)
        {
            WorkerServiceType serviceType = new WorkerServiceType();
            WorkerServiceTypeEntity workerServiceTypeEntity = entityList.get(i);
            serviceType.setServiceTypeId(workerServiceTypeEntity.getServiceTypeId());
            serviceType.setServiceTypeName(workerServiceTypeEntity.getServiceTypeName());
            list.add(serviceType);
        }
        workerServiceTypeInfo.setList(list);
        return workerServiceTypeInfo;

    }

    public BusinessServiceTypeInfo transform(List<ServiceContent> list){
        List<BusinessServiceType> results = new ArrayList<>();
        for (ServiceContent item :
                list) {
            Log.i(TAG, "transform: "+item.getServiceName()+item.getServiceTypeId());
//            results.add(new BusinessServiceType(item.getServiceTypeId(),item.getServiceTypeName()));

        }

        return new BusinessServiceTypeInfo(results);
    }

}
