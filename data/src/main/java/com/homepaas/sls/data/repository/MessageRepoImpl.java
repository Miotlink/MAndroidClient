package com.homepaas.sls.data.repository;

import android.util.Log;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.MessageMDataSource;
import com.homepaas.sls.data.repository.datasource.MessageNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.Message;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.SaveDataException;
import com.homepaas.sls.domain.repository.MessageRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
@Singleton
public class MessageRepoImpl implements MessageRepo {

    private static final String TAG = "MessageRepoImpl";

    @Inject
    MessageMDataSource messageMDataSource;

    private MessageNDataSource nDataSource;

    private PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public MessageRepoImpl(MessageNDataSource nDataSource, PersonalInfoPDataSource pDataSource) {
        this.nDataSource = nDataSource;
        this.personalInfoPDataSource = pDataSource;
    }

    @Override
    public List<Message> getMessageList() throws GetDataException, AuthException {
        try {
            String token = personalInfoPDataSource.getToken();
            return nDataSource.getMessageList(token);
        } catch (GetNetworkDataException e){
            throw new GetDataException(Constant.NETWORK_ERROR);
        }catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        } catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                personalInfoPDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "readMessages: ",e1);
            }
            throw new AuthException(e.getMessage(), e);
        }
    }

    @Override
    public int getUnreadCount() throws GetDataException, AuthException {
        try {
            String token = personalInfoPDataSource.getToken();
            return nDataSource.getUnreadMessageCount(token);
        } catch (GetNetworkDataException e){
            throw new GetDataException(Constant.NETWORK_ERROR);
        }catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        } catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                personalInfoPDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "readMessages: ",e1);
            }
            throw new AuthException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteMessages(String... ids) throws SaveDataException, AuthException {
        try {
            String token = personalInfoPDataSource.getToken();
            nDataSource.deleteMessages(token, ids);
        } catch (GetNetworkDataException e){
            throw new SaveDataException(Constant.NETWORK_ERROR);
        }catch (ResponseMetaDataException e) {
            throw new SaveDataException(e.getMessage(), e);
        } catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                personalInfoPDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "readMessages: ",e1);
            }
            throw new AuthException(e.getMessage(), e);
        }
    }

    @Override
    public void readMessages(String... ids) throws SaveDataException, AuthException {
        try {
            String token = personalInfoPDataSource.getToken();
            nDataSource.readMessages(token, ids);
        } catch (GetNetworkDataException e){
            throw new SaveDataException(Constant.NETWORK_ERROR);
        }catch (ResponseMetaDataException e) {
            throw new SaveDataException(e.getMessage(), e);
        } catch (ResponseMetaAuthException | GetPersistenceDataException e) {
            try {
                personalInfoPDataSource.clearToken();
            } catch (PersistDataException e1) {
                Log.e(TAG, "readMessages: ",e1);
            }
            throw new AuthException(e.getMessage(), e);
        }
    }
}
