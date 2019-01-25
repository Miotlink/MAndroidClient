package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.SearchHotServiceRequest;
import com.homepaas.sls.data.network.dataformat.request.SearchServiceRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.HotServiceInfo;
import com.homepaas.sls.domain.repository.HotServiceInfoRepo;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2016/12/21.
 */

public class HotServiceInfoRepoImpl implements HotServiceInfoRepo {

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public HotServiceInfoRepoImpl() {
    }

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Override
    public Observable<List<HotServiceInfo>> getHotServiceInfoList(String latitude, String longitude) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            SearchHotServiceRequest request = new SearchHotServiceRequest(latitude, longitude, token);
            return apiHelper.restApi()
                    .getHotServiceInfo(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<List<HotServiceInfo>>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
