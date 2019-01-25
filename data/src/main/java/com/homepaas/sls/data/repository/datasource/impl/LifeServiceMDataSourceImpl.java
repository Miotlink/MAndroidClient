package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.repository.datasource.LifeServiceMDataSource;
import com.homepaas.sls.domain.entity.LifeService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/1/21.
 */

@Singleton
public class LifeServiceMDataSourceImpl implements LifeServiceMDataSource {

    @Inject
    public LifeServiceMDataSourceImpl() {}

    @Override
    public List<LifeService> getLifeServiceList() {
        return MockData.getFakeServices();
    }
}
