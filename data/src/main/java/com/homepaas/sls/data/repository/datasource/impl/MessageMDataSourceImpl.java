package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.repository.datasource.MessageMDataSource;
import com.homepaas.sls.domain.entity.Message;
import com.homepaas.sls.domain.exception.GetDataException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
@Singleton
public class MessageMDataSourceImpl implements MessageMDataSource {

    @Inject
    public MessageMDataSourceImpl() {
    }

    @Override
    public List<Message> getMessageList() throws GetDataException {
        List<Message> messages = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            messages.add(MockData.createFakeMessage());
        }
        return messages;
    }

    @Override
    public int getUnreadMessageCount() throws GetDataException {
        return 4;
    }
}
