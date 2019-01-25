package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

/**
 * on 2016/7/1 0001
 *
 * @author zhudongjie
 */
public interface ShareRepo {

    ShareInfo get(int id) throws GetDataException, AuthException;

    String addShareRecord(int id) throws GetDataException, AuthException;
}
