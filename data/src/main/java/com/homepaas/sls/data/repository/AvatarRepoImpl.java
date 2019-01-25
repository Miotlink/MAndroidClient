package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.AvatarsNDataSource;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.AvatarRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sherily on 2016/9/18.
 */
@Singleton
public class AvatarRepoImpl implements AvatarRepo {
    @Inject
    AvatarsNDataSource avatarsNDataSource;

    @Inject
    public AvatarRepoImpl() {
    }

    @Override
    public AvatarsEntity geAvatars(String type, String id) throws GetDataException, AuthException {
        try {
            return avatarsNDataSource.getAvatars(type,id);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }
}
