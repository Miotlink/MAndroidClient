package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.repository.datasource.LifeServiceLDataSource;
import com.homepaas.sls.data.repository.datasource.LifeServiceMDataSource;
import com.homepaas.sls.data.repository.datasource.LifeServiceNDataSource;
import com.homepaas.sls.domain.entity.LifeService;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.LifeServiceRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Administrator on 2015/12/22.
 */

@Singleton
public class LifeServiceRepoImpl implements LifeServiceRepo {

    @Inject
    LifeServiceMDataSource dataSource;

    @Inject
    LifeServiceNDataSource nDataSource;

    @Inject
    LifeServiceLDataSource lDataSource;

    @Inject
    public LifeServiceRepoImpl() {
        //
    }

    @Override
    public List<LifeService> getLifeServiceList() throws GetDataException {
//        try {
//            return nDataSource.getLifeServiceList();
//        } catch (GetNetworkDataException e) {
//            throw new GetDataException(Constant.NETWORK_ERROR,e);
//        } catch (ResponseMetaDataException e) {
//            throw new GetDataException(e.getMessage(),e);
//        }
        //V2.0.2版本写死到本地
        return lDataSource.getLifeServiceList();
    }

    @Override
    public List<LifeService> getHotLifeServiceList() throws GetDataException {
        try {
            return nDataSource.getHotLifeServiceList();
        }  catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        }
    }
}
