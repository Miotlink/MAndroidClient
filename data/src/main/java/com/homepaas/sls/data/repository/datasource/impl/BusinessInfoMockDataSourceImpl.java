package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.repository.datasource.BusinessInfoMockDataSource;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.exception.GetDataException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.homepaas.sls.data.repository.datasource.impl.MockData.createBusinessInfo;

/**
 * on 2016/1/18 0018
 *
 * @author zhudongjie .
 */
@Singleton
public class BusinessInfoMockDataSourceImpl implements BusinessInfoMockDataSource {


    @Inject
    public BusinessInfoMockDataSourceImpl() {
    }

    @Override
    public BusinessInfo getBusinessInfo(String businessId) throws GetDataException {
        return createBusinessInfo();
    }

    @Override
    public List<BusinessInfo> getBusinessInfoList() throws GetDataException {
        List<BusinessInfo> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(createBusinessInfo());
        }
        return list;
    }

}
