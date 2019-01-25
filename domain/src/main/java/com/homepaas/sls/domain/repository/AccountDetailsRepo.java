package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.AccountDetailItemEntry;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/6.
 */

public interface AccountDetailsRepo {
    Observable<AccountDetailItemEntry> getAccountDetail(int id);
}
