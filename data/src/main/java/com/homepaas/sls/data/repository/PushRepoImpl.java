package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.repository.datasource.PersonalInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PushNDataSource;
import com.homepaas.sls.domain.repository.PushRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by CJJ on 2016/7/29.
 */
@Singleton
public class PushRepoImpl implements PushRepo {


    @Inject
    PushNDataSource pushNDataSource;


    @Inject
    public PushRepoImpl() {
    }

    @Override
    public String uploadPushCleintId(String clientId) {
        String s = pushNDataSource.uploadClientId(clientId);
        return s;
    }
}
