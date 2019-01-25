package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

/**
 * on 2016/7/11 0011
 *
 * @author zhudongjie
 */
public interface OtherRepo {

    PushInfo loadQrCodeUrl(String url)throws GetDataException, AuthException;
}
