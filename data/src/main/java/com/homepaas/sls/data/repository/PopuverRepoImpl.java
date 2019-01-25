package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.PopuVerNDataSource;
import com.homepaas.sls.domain.entity.PopupVer;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.PopuverRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/12/27.
 */
@Singleton
public class PopuverRepoImpl implements PopuverRepo {
    @Inject
    PopuVerNDataSource popuVerNDataSource;

    @Inject
    public PopuverRepoImpl() {
    }

    @Override
    public PopupVer getPopuVer() throws GetDataException, AuthException {
        try {
            return popuVerNDataSource.getPopuVer();
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }
}
