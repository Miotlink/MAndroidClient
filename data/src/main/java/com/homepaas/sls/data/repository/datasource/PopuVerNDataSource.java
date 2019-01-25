package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.PopupVer;

/**
 * Created by Administrator on 2016/12/27.
 */

public interface PopuVerNDataSource {
    PopupVer getPopuVer() throws ResponseMetaAuthException, ResponseMetaDataException, GetNetworkDataException;
}
