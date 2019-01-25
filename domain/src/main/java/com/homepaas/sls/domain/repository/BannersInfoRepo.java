package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.BannersInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

import java.util.List;

/**
 * Created by Sherily on 2016/9/10.
 */
public interface BannersInfoRepo {
    List<BannerInfo> getBannersInfo()throws GetDataException, AuthException;
}
