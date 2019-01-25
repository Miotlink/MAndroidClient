package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.entity.MessageEntity;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.Message;

import java.util.List;

/**
 * on 2016/2/3 0003
 *
 * @author zhudongjie .
 */
public interface MessageNDataSource {

    List<Message> getMessageList(String token) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;

    int getUnreadMessageCount(String token) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;

    int deleteMessages(String token,String... ids) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;

    int readMessages(String token,String... ids)throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException;
}
