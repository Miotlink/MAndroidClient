package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.domain.entity.PushInfo;

/**
 * on 2016/7/11 0011
 *
 * @author zhudongjie
 */
public interface OtherInfoNDataSource {

    PushInfo loadUrl(String token,String url)throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;
}
