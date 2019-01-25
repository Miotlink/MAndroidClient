package com.homepaas.sls.data.repository.datasource.impl;

import android.util.Log;

import com.homepaas.sls.data.BuildConfig;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.repository.datasource.AddressNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.AddressIdEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.param.AddServiceAddressParam;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by CJJ on 2016/9/13.
 *
 */
@Singleton
public class AddressNDataSourceImpl implements AddressNDataSource {

    private static final String TAG = "AddressNDataSourceImpl";
    @Inject
    public AddressNDataSourceImpl() {
    }

    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Override
    public List<AddressEntity> getAddress() throws AuthException, ResponseMetaDataException {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<List<AddressEntity>>> response = apiHelper.getServiceAddressList(token);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid())
                {
                    List<AddressEntity> data = response.body().data;
                    return data;
                }else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new ResponseMetaDataException("错误响应码:" + response.code());

        } catch (GetPersistenceDataException |ResponseMetaAuthException e ) {
           throw new AuthException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteServiceAddress(String id) throws AuthException {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<Void>> response = apiHelper.deleteServiceAddress(token,id);
            Meta meta = response.body().meta;
            if (meta.isValid())
            {
                return "";
            }
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e);
        }
        return null;
    }

    @Override
    public String addServiceAddress(AddressEntity param) throws AuthException {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<AddressIdEntity>> response = apiHelper.addServiceAddress(token,param);
            Meta meta = response.body().meta;
            if (meta.isValid())
            {
                return response.body().data.getId();
            }
        } catch (GetPersistenceDataException |ResponseMetaAuthException e ) {
            throw new AuthException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateServiceAddress(AddressEntity param) throws AuthException {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<Void>> response = apiHelper.updateServiceAddress(token,param);
            Meta meta = response.body().meta;
            if (meta.isValid())
            {
                if (BuildConfig.DEBUG)
                    Log.i(TAG, "updateServiceAddress: "+meta.getErrorMsg());
                return "";
            }
        } catch (GetPersistenceDataException |ResponseMetaAuthException e ) {
            throw new AuthException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
