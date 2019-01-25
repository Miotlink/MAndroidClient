package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.param.AddServiceAddressParam;

import java.util.List;

/**
 * Created by CJJ on 2016/9/13.
 */

public interface AddressNDataSource {
    List<AddressEntity> getAddress() throws AuthException, ResponseMetaDataException;
    String deleteServiceAddress(String id) throws AuthException;

    String addServiceAddress(AddressEntity param) throws AuthException;

    String updateServiceAddress(AddressEntity param) throws AuthException;
}
