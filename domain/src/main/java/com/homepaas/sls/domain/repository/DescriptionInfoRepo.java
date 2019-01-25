package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.DescriptionInfo;

import rx.Observable;

/**
 * Created by CJJ on 2016/7/13.
 */
public interface DescriptionInfoRepo {

    Observable<DescriptionInfo> getDescription(String code, String serviceTypeId);
}
