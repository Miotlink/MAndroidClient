package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.AliPaySignRequest;
import com.homepaas.sls.data.network.dataformat.request.WXPaySignRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.AliPaySignEntry;
import com.homepaas.sls.domain.entity.WXPaySignEntry;
import com.homepaas.sls.domain.repository.WxpaySignExRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**ex
 * Created by Administrator on 2016/12/6.
 */

@Singleton
public class WxpaySignExRepoImpl implements WxpaySignExRepo {

    @Inject
    public WxpaySignExRepoImpl() {
    }

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    RestApiHelper restApiHelper;

    @Override
    public Observable<WXPaySignEntry> getWxpaySign(String activityId, String neddPay, String totalMoney, String activity) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            WXPaySignRequest request = new WXPaySignRequest(token, activityId, neddPay, totalMoney, activity);
            return restApiHelper.restApi()
                    .getWXPaySign(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<WXPaySignEntry>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
