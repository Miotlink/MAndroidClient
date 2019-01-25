package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.AliPaySignEntry;
import com.homepaas.sls.domain.entity.WXPaySignEntry;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/6.
 */

public interface WxpaySignExRepo {
    Observable<WXPaySignEntry> getWxpaySign(String activityId, String neddPay, String totalMoney, String activity);
}
