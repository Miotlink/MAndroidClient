package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.Message;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.SaveDataException;

import java.util.List;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
public interface MessageRepo {

    List<Message> getMessageList() throws GetDataException,AuthException;

    int getUnreadCount() throws GetDataException,AuthException;

    void deleteMessages(String... ids) throws SaveDataException,AuthException;

    void readMessages(String... ids)throws SaveDataException,AuthException;
}
