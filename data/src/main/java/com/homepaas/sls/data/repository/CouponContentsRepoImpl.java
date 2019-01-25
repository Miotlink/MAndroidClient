package com.homepaas.sls.data.repository;

import android.util.Log;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.CouponContentNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.CouponContentsInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.CouponContentsRepo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sheirly on 2016/6/23.
 */
@Singleton
public class CouponContentsRepoImpl implements CouponContentsRepo {

    private static final String TAG = "CouponContentsRepoImpl";
    @Inject
    CouponContentNDataSource couponContentNDataSource;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    public CouponContentsRepoImpl() {
    }



    @Override
    public CouponContentsInfo getCouponContentsInfo(String serviceId, String longitude,String latitude,String ispay) throws GetDataException, AuthException {

        try {
            String token = personalInfoPDataSource.getToken();
            CouponContentsInfo couponContentsInfo = couponContentNDataSource.getCouponList(token,serviceId,longitude,latitude,ispay);
            return couponContentsInfo;
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR);
        }catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                personalInfoPDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "readMessages: ",e1);
            }
            throw new AuthException(e.getMessage(), e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GetDataException(Constant.NETWORK_ERROR);
        }   catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }
}
