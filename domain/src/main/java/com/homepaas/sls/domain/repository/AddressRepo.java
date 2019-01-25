package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.param.AddServiceAddressParam;

import java.util.List;

/**
 * Created by CJJ on 2016/9/13.
 */

public interface AddressRepo {

    List<AddressEntity> getAddress() throws AuthException, GetDataException;
    String deleteAddress(String id) throws AuthException;

    String addServiceAddress(AddressEntity param) throws AuthException;

    String updateServiceAddress(AddressEntity param) throws AuthException;
}
