package com.homepaas.sls.data.repository;

import android.util.Log;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.OtherInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.OtherRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/7/11 0011
 *
 * @author zhudongjie
 */
@Singleton
public class OtherRepoImpl implements OtherRepo {

    private static final String TAG = "OtherRepoImpl";
    private OtherInfoNDataSource nDataSource;
    private PersonalInfoPDataSource infoPDataSource;

    @Inject
    public OtherRepoImpl(OtherInfoNDataSource nDataSource, PersonalInfoPDataSource infoPDataSource) {
        this.nDataSource = nDataSource;
        this.infoPDataSource = infoPDataSource;
    }


    @Override
    public PushInfo loadQrCodeUrl(String url) throws GetDataException, AuthException {
        try {
            String token = infoPDataSource.getToken();
            return nDataSource.loadUrl(token,url);
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            try {
                infoPDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "loadQrCodeUrl: ",e1 );
            }
            throw new AuthException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }
}
