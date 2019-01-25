package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.entity.mapper.MessageMapper;
import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.MessageBody;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.UnreadMessageBody;
import com.homepaas.sls.data.network.dataformat.request.DeleteMessageRequest;
import com.homepaas.sls.data.network.dataformat.request.ReadMessageRequest;
import com.homepaas.sls.data.repository.datasource.MessageNDataSource;
import com.homepaas.sls.domain.entity.Message;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * on 2016/2/3 0003
 *
 * @author zhudongjie .
 */
@Singleton
public class MessageNDataSourceImpl implements MessageNDataSource {

    private RestApiHelper apiHelper;

    private MessageMapper messageMapper;



    @Inject
    public MessageNDataSourceImpl(RestApiHelper apiHelper, MessageMapper messageMapper) {
        this.apiHelper = apiHelper;
        this.messageMapper = messageMapper;
    }

    @Override
    public List<Message> getMessageList(String token) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException{
        try {
            Response<ResponseWrapper<MessageBody>> response = apiHelper.getMessageList(token);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    List<Message> messages = response.body().data.getMessageEntities();
//                    return messageMapper.transform(messageEntities);
                        return messages;
                } else {
                    throw new ResponseMetaDataException("Error Code " + meta.getErrorCode() + " " + meta.getErrorMsg());
                }
            }
            throw new ResponseMetaDataException("错误响应码:" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public int getUnreadMessageCount(String token) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException{
        try {
            Response<ResponseWrapper<UnreadMessageBody>> response = apiHelper.getUnreadMessageCount(token);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.getCount();
                } else {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            }
            throw new ResponseMetaDataException("错误响应码:" + response.code());
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
    }

    @Override
    public int deleteMessages(String token,String... ids) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException {
        DeleteMessageRequest request = new DeleteMessageRequest(token, ids);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.deleteMessages(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (!meta.isValid()) {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            } else {
                throw new ResponseMetaDataException("错误响应码" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
        return 0;
    }

    @Override
    public int readMessages(String token,String... ids) throws GetNetworkDataException, ResponseMetaAuthException, ResponseMetaDataException{
        ReadMessageRequest request = new ReadMessageRequest(token, ids);
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.readMessages(request);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (!meta.isValid()) {
                    throw new ResponseMetaDataException(meta.getErrorMsg());
                }
            } else {
                throw new ResponseMetaDataException("错误响应码" + response.code());
            }
        } catch (IOException e) {
            throw new GetNetworkDataException(e);
        }
        return 0;
    }


}
