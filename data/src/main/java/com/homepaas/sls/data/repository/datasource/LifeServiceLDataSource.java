package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.domain.entity.LifeService;

import java.util.List;

/**
 * Created by fmisser on 2016/7/29.
 * V2.0.2版本在本地写死服务列表数据
 */

public interface LifeServiceLDataSource {
    public List<LifeService> getLifeServiceList();
}
