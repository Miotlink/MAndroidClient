package com.homepaas.sls.data.repository.datasource;

import android.support.annotation.Nullable;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.ShareInfo;

/**
 * on 2016/7/1 0001
 *
 * @author zhudongjie
 */
public interface ShareNDataSource {

    ShareInfo get(@Nullable String token, int id) throws GetNetworkDataException, ResponseMetaDataException, ResponseMetaAuthException;

    String addShareRecord(String token, int id)throws GetNetworkDataException, ResponseMetaDataException, ResponseMetaAuthException;
}
