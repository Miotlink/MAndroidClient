package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.GetDescriptionRequest;
import com.homepaas.sls.data.repository.datasource.DescriptionNDataSource;
import com.homepaas.sls.domain.entity.DescriptionInfo;
import com.homepaas.sls.domain.repository.DescriptionInfoRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by CJJ on 2016/7/13.
 *
 */
@Singleton
public class DescriptionInfoRepoImpl implements DescriptionInfoRepo{

    @Inject
    RestApiHelper restApiHelper;

    @Inject
    public DescriptionInfoRepoImpl() {
    }

    @Override
    public Observable<DescriptionInfo> getDescription(String code, String id) {
        GetDescriptionRequest request = new GetDescriptionRequest(code,id);
        return restApiHelper.restApi()
                .getDescription(request)
                .flatMap(new RestApiHelper.ResponseParseFunc<DescriptionInfo>());
    }
}
