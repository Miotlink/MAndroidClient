package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.TopicRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.ServiceItemListEntity;
import com.homepaas.sls.domain.repository.SearchTopicRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Sherily on 2017/3/27.
 */

public class SearchTopicRepoImpl implements SearchTopicRepo{
    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public SearchTopicRepoImpl() {
    }

    @Override
    public Observable<ServiceItemListEntity> getQueryService(String longitude, String latitude, String queryStr) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            return apiHelper.restApi()
                    .getSearchTopic(new TopicRequest(token,longitude,latitude,queryStr,"1"))
                    .flatMap(new RestApiHelper.ResponseParseFunc<ServiceItemListEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
