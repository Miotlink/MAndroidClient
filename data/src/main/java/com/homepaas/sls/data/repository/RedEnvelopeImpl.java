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
import com.homepaas.sls.domain.repository.RedEnvelopeRepo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/6/21.
 * 优惠券数据仓库
 */
@Singleton
public class RedEnvelopeImpl implements RedEnvelopeRepo {

    private static final String TAG = "RED_repoimpl";

    @Inject
    CouponContentNDataSource couponContentNDataSource;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public RedEnvelopeImpl() {
    }

    @Override
    public CouponContentsInfo getRedEnvelopeInfo(String status) throws GetDataException, AuthException {
        try {
            String token = personalInfoPDataSource.getToken();
            CouponContentsInfo couponContentsInfo = couponContentNDataSource.getCouponList(token,null,null,null,null);
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
        }   catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
        return null;
    }
}
