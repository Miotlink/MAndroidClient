package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AvatarInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.SystemConfigEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.param.PriceParam;

import java.util.List;

/**
 * Created by CJJ on 2016/9/14.
 */

public interface ServiceNDataSource {
    List<ServiceScheduleEntity> getServiceDSchedule(String serviceTypId) throws AuthException;

    PriceEntity getServicePrice(PriceParam param) throws AuthException;

    ActivityNgInfoNew getAvailableActivity(String serviceTypeId) throws AuthException;

    SystemConfigEntity getConfig() throws AuthException;

    List<String> getQty(String serviceTypeId);
}
