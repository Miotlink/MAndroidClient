package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.GetStarLevelRequest;
import com.homepaas.sls.domain.entity.StarLevelInfo;
import com.homepaas.sls.domain.repository.GetStarLevelRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/7/21.
 */

public class GetStarLevelImpl implements GetStarLevelRepo {
    @Inject
    RestApiHelper apiHelper;

    @Inject
    public GetStarLevelImpl() {
    }

    @Override
    public Observable<StarLevelInfo> GetStarLevel(String serviceTypeId) {
        GetStarLevelRequest request = new GetStarLevelRequest(serviceTypeId);
        return apiHelper.restApi()
                .getStarLevel(request)
                .flatMap(new RestApiHelper.ResponseParseFunc<StarLevelInfo>());
    }
}
