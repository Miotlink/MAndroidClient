package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.InvalidPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.domain.entity.BusinessInfo;

import java.util.List;

/**
 * on 2016/2/23 0023
 *
 * @author zhudongjie .
 */
public interface BusinessInfoPDataSource {

    BusinessInfo get(String businessId) throws GetPersistenceDataException, InvalidPersistenceDataException;

    List<BusinessInfo> getCollectedBusinesses() throws GetPersistenceDataException, InvalidPersistenceDataException;

    List<BusinessInfo> getSearchedBusinesses() throws GetPersistenceDataException, InvalidPersistenceDataException;

    void save(BusinessInfo business, int dataFrom) throws PersistDataException;

    void save(List<BusinessInfo> businesses, int dataFrom) throws PersistDataException;
}
