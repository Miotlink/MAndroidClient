package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.AliPaySignEntry;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/6.
 */

public interface AlipaySignExRepo {
    Observable<AliPaySignEntry> getAlipaySign(String activityId, String neddPay, String totalMoney, String activity);
}
