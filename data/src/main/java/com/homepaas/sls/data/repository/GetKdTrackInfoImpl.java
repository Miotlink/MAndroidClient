package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.GetKdTrackInfoRequest;
import com.homepaas.sls.domain.entity.KdTrackInfoResponse;
import com.homepaas.sls.domain.repository.GetKdTrackInfoRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/6/5.
 */

public class GetKdTrackInfoImpl implements GetKdTrackInfoRepo {
    @Inject
    public GetKdTrackInfoImpl() {
    }

    @Inject
    RestApiHelper apiHelper;

    @Override
    public Observable<KdTrackInfoResponse> getKdTrackInfo(String logisticCode, String shipperCode) {
        GetKdTrackInfoRequest request = new GetKdTrackInfoRequest(logisticCode, shipperCode);
        return apiHelper.restApi()
                .getKdTrackInfo(request)
                .flatMap(new RestApiHelper.ResponseParseFunc<KdTrackInfoResponse>());
    }
}
