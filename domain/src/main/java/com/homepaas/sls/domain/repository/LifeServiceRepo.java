package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.LifeService;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.domain.exception.GetDataException;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2015/12/22.
 *
 */

public interface LifeServiceRepo {

    List<LifeService> getLifeServiceList() throws GetDataException;

    List<LifeService> getHotLifeServiceList() throws GetDataException;
}
