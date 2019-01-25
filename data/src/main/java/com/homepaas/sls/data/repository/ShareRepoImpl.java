package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.ShareNDataSource;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.repository.ShareRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/7/1 0001
 *
 * @author zhudongjie
 */
@Singleton
public class ShareRepoImpl implements ShareRepo {

    private ShareNDataSource nDataSource;

    private PersonalInfoPDataSource pDataSource;

    @Inject
    public ShareRepoImpl(ShareNDataSource nDataSource, PersonalInfoPDataSource pDataSource) {
        this.nDataSource = nDataSource;
        this.pDataSource = pDataSource;
    }


    @Override
    public ShareInfo get(int id) throws GetDataException, AuthException {
        try {
            String token  = pDataSource.getToken();
            return nDataSource.get(token,id);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        } catch (GetPersistenceDataException |ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(),e);
        }
    }



    @Override
    public String addShareRecord(int id) throws GetDataException, AuthException{

        try {
            String token = pDataSource.getToken();
            return nDataSource.addShareRecord(token,id);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR,e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(),e);
        } catch (GetPersistenceDataException |ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(),e);
        }

    }
}
