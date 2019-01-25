package com.homepaas.sls.data.entity.mapper;

import com.homepaas.sls.data.network.dataformat.BusinessTagsBody;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/9/9.
 */
@Singleton
public class BusinessTagsInfoMapper {
    @Inject
    public BusinessTagsInfoMapper() {
    }

    public GetBusinessTagsInfo transform(BusinessTagsBody businessTagsBody){
        GetBusinessTagsInfo getBusinessTagsInfo = new GetBusinessTagsInfo();
        getBusinessTagsInfo.setCount(businessTagsBody.getCount());
        getBusinessTagsInfo.setBusinessTagsInfos(businessTagsBody.getBusinessTagsInfos());
        return getBusinessTagsInfo;
    }
}
