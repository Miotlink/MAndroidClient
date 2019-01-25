package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.BannersInfo;

import java.util.List;

/**
 * Created by Sherily on 2016/9/10.
 */
public interface BannersInfoNDataSource {
    List<BannerInfo> getBannersInfo() throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;
}
