package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.AccountDetailsRequest;
import com.homepaas.sls.data.network.dataformat.request.AliPaySignRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.AccountDetailItemEntry;
import com.homepaas.sls.domain.entity.AliPaySignEntry;
import com.homepaas.sls.domain.repository.AlipaySignExRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/6.
 */

@Singleton
public class AlipaySignExRepoImpl implements AlipaySignExRepo {

    @Inject
    public AlipaySignExRepoImpl() {
    }

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    RestApiHelper restApiHelper;

    @Override
    public Observable<AliPaySignEntry> getAlipaySign(String activityId, String neddPay, String totalMoney, String activity) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            AliPaySignRequest request = new AliPaySignRequest(token, activityId, neddPay, totalMoney, activity);
            return restApiHelper.restApi()
                    .getAlipaySign(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<AliPaySignEntry>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
