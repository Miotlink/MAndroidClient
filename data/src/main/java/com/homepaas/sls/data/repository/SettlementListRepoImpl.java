package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.SettlementListRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.AccountListEntity;
import com.homepaas.sls.domain.entity.SettlementDetailResponse;
import com.homepaas.sls.domain.repository.SettlementListRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/5.
 */

public class SettlementListRepoImpl implements SettlementListRepository {

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public SettlementListRepoImpl() {

    }

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Override
    public Observable<AccountListEntity> getSettlementList(String isMinus, int pageIndex, int pageSize) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            SettlementListRequest settlemetListRequest = new SettlementListRequest(token, isMinus, pageIndex, pageSize);
            return apiHelper.restApi()
                    .getSettlementList(settlemetListRequest)
                    .flatMap(new RestApiHelper.ResponseParseFunc<AccountListEntity>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }


}
