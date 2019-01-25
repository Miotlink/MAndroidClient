package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.ServiceTimeStartAtEntry;

import java.util.List;

import rx.Observable;

/**
 * Created by JWC on 2017/3/28.
 */

public interface ServiceTimeStartAtRepo {
    Observable<ServiceTimeStartAtEntry> getServiceTimeStartAt(String serviceId, String longitude, String latitude);
}
