package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.exception.GetDataException;

import java.util.List;

/**
 * on 2016/1/18 0018
 *
 * @author zhudongjie .
 */
public interface BusinessInfoMockDataSource {
         BusinessInfo getBusinessInfo(String businessId) throws GetDataException;
        List<BusinessInfo> getBusinessInfoList()throws GetDataException;
}
