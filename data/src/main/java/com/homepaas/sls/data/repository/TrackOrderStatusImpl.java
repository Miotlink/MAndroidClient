package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.TrackOrderStatusRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.TrackOrderInfo;
import com.homepaas.sls.domain.repository.TrackOrderStatusRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/7/19.
 */

public class TrackOrderStatusImpl implements TrackOrderStatusRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public TrackOrderStatusImpl() {
    }

    @Override
    public Observable<TrackOrderInfo> getTrackOrderStatus(String orderId) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            TrackOrderStatusRequest request = new TrackOrderStatusRequest(token, orderId);
            return apiHelper.restApi()
                    .getTrackOrderStatus(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<TrackOrderInfo>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
