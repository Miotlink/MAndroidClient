package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ServiceTypeEx;

import java.util.List;

import rx.Observable;

/**
 * Date: 2016/9/13.
 * Author: fmisser
 * Describe: 服务类型列表仓库接口
 */

public interface ServiceTypeExRepository {
    Observable<List<ServiceTypeEx>> getInfoListEx();
}
