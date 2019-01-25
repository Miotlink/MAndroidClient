package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.exception.AuthException;

/**
 * Created by Sherily on 2016/9/18.
 */
public interface AvatarsNDataSource {
    AvatarsEntity getAvatars(String type, String id) throws GetNetworkDataException, AuthException, ResponseMetaAuthException, ResponseMetaDataException;
}
