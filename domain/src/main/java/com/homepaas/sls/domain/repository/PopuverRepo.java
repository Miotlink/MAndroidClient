package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.PopupVer;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;

/**
 * Created by Administrator on 2016/12/27.
 */

public interface PopuverRepo {
    PopupVer getPopuVer()throws GetDataException, AuthException;
}
