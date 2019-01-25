package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.RechargeInfoRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.domain.entity.RechargeInfoEntry;
import com.homepaas.sls.domain.entity.RechargeInfoResponse;
import com.homepaas.sls.domain.repository.RechargeInfoRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/7.
 */

public class RechargeInfoRepoImpl implements RechargeInfoRepo {

    @Inject
    public RechargeInfoRepoImpl() {
    }

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    RestApiHelper restApiHelper;

    @Override
    public Observable<RechargeInfoResponse> getRechargeInfo(String rechargeCode) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            RechargeInfoRequest request = new RechargeInfoRequest(token, rechargeCode);
            return restApiHelper.restApi()
                    .getRechargeInfo(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<RechargeInfoResponse>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
