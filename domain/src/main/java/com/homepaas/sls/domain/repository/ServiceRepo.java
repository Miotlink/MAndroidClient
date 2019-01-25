package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AvatarInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.SystemConfigEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.param.PriceParam;

import java.util.List;

import rx.Observable;

/**
 * Created by CJJ on 2016/9/14.
 * 服务仓库，获取指定服务的零散信息，针对无法一次性返回的信息，需要请求独立的接口获取这些信息，
 * 比如指定服务的服务单价、工人或者商户的头像、服务的服务时间表等,对于后台而言，就是系统(System)级别的api
 */

public interface ServiceRepo {

    List<ServiceScheduleEntity> getServiceDSchedule(String serviceTypId) throws AuthException;
    PriceEntity getServicePrice(PriceParam param) throws AuthException;
    ActivityNgInfoNew getAvailableActivity(String serviceTypeId) throws AuthException;

    SystemConfigEntity getConfig() throws AuthException;
    Observable<List<String>> getQty(String serviceTypeId);
}
