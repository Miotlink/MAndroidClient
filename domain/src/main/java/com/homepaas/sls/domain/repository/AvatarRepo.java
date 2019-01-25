package com.homepaas.sls.domain.repository;


import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

/**
 * Created by Sherily on 2016/9/18.
 */
public interface AvatarRepo {
    AvatarsEntity geAvatars(String type, String id)throws GetDataException, AuthException;
}
