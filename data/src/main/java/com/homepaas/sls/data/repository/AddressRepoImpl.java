package com.homepaas.sls.data.repository;


import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.AddressNDataSource;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.param.AddServiceAddressParam;
import com.homepaas.sls.domain.repository.AddressRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by CJJ on 2016/9/13.
 *
 */
@Singleton
public class AddressRepoImpl implements AddressRepo {


    @Inject
    public AddressRepoImpl() {
    }

    @Inject
    AddressNDataSource addressNDataSource;
    @Override
    public List<AddressEntity> getAddress() throws AuthException, GetDataException {
        try {
            return addressNDataSource.getAddress();
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }

    @Override
    public String deleteAddress(String id) throws AuthException {
        return addressNDataSource.deleteServiceAddress(id);
    }

    @Override
    public String addServiceAddress(AddressEntity param) throws AuthException {
        return addressNDataSource.addServiceAddress(param);
    }

    @Override
    public String updateServiceAddress(AddressEntity param) throws AuthException {
        return addressNDataSource.updateServiceAddress(param);
    }
}
