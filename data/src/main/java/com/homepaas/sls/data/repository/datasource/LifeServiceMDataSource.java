package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.domain.entity.LifeService;

import java.util.List;

/**
 * Created by Administrator on 2016/1/21.
 *
 */

public interface LifeServiceMDataSource {
    public List<LifeService> getLifeServiceList();
}
