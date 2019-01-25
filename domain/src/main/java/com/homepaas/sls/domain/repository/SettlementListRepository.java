package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.AccountListEntity;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.domain.entity.SettlementDetailResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by JWC on 2016/12/5.
 */

public interface SettlementListRepository {
    Observable<AccountListEntity> getSettlementList(String isMinus, int pageIndex, int pageSize);
}
