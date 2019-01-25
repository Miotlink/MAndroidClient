package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.entity.mapper.CouponContentMapper;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.CouponContentsBody;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.GetCouponContentsRequest;
import com.homepaas.sls.data.repository.datasource.CouponContentNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.CouponContentsInfo;


import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Sheirly on 2016/6/23.
 */
@Singleton
public class CouponContentNDataSourceImpl implements CouponContentNDataSource {

    private RestApiHelper apiHelper;

    private PersonalInfoPDataSource personalInfoPDataSource;

    private CouponContentMapper couponContentMapper;

    @Inject
    public CouponContentNDataSourceImpl(RestApiHelper apiHelper, PersonalInfoPDataSource personalInfoPDataSource, CouponContentMapper couponContentMapper) {
        this.apiHelper = apiHelper;
        this.personalInfoPDataSource = personalInfoPDataSource;
        this.couponContentMapper = couponContentMapper;
    }

    /**
     * 获取优惠券列表
     * @param token
     * @return
     * @throws GetNetworkDataException
     * @throws ResponseMetaAuthException
     * @throws ResponseMetaDataException
     * @throws GetPersistenceDataException
     * @throws IOException
     */
    @Override
    public CouponContentsInfo getCouponList(String token,String serviceId, String longitude,String latitude,String ispay) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException, GetPersistenceDataException, IOException {
        CouponContentsInfo couponContentsInfo = new CouponContentsInfo();
        token = personalInfoPDataSource.getToken();
        GetCouponContentsRequest request = new GetCouponContentsRequest(token,serviceId,longitude,latitude,ispay);
        Response<ResponseWrapper<CouponContentsBody>> response = apiHelper.getCouponList(request);
        if (response.code() == 200) {
            Meta meta = response.body().meta;
            if (meta.isValid()) {
                CouponContentsBody data = response.body().data;
                if (data!=null) {
                    couponContentsInfo = couponContentMapper.transform(data.getCouponContentsEntities());
                } else {
                    couponContentsInfo = new CouponContentsInfo();
                    couponContentsInfo.setCouponContentsList(new ArrayList<CouponContents>());

                }
            } else {
                throw new ResponseMetaDataException(response.body().meta.getErrorMsg());
            }
        }
        return couponContentsInfo;
    }
}
